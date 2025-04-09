let currentPage = 0;
function onOpenEmpresa() {
    const priorBtn = document.getElementById('priorBtnEmp');
    const nextBtn = document.getElementById('nextBtnEmp');
    const overlay = document.querySelector('.overlay');
    const divAdd = document.querySelector('.divAddEmp');
    const divEdit = document.querySelector('.divEditEmp');
    const divDelete = document.querySelector('.divDeleteEmp');
    let currentid;

    nextDataPageEmpresas();

    nextBtn.addEventListener('click', () => {
        currentPage++;
        nextDataPageEmpresas();
    });

    priorBtn.addEventListener('click', () => {
        currentPage--;
        if (currentPage < 0) {
            currentPage = 0;
        }
        nextDataPageEmpresas();
    });

    document.querySelector('.tableEmp').addEventListener('click', (event) => {

        if(event.target.classList.contains('imgEdit')) {
            const { currentNomeSo, currentTelSo, currentNomeFa,
                currentRazao, currentCnpj, currentInscricao,
                currentEmail, currentTelEmp, currentPorte, currentRamo,
                currentCep, currentUf, currentBairro, currentCidade, currentNumero,
                currentLogradouro } = processEventEmpresas(event);


            currentid = event.target.getAttribute('data-id');
            document.getElementById('nomeFaEdit').value = currentNomeFa;
            document.getElementById('nomeSoEdit').value = currentNomeSo;
            document.getElementById('telSoEdit').value = currentTelSo;
            document.getElementById('razaoEdit').value = currentRazao;
            document.getElementById('cnpjEdit').value = currentCnpj;
            document.getElementById('inscricaoEdit').value = currentInscricao;
            document.getElementById('emailEdit').value = currentEmail;
            document.getElementById('telEmpEdit').value = currentTelEmp;
            document.getElementById('ramoEdit').value = currentRamo;
            document.getElementById('porteEdit').value = currentPorte;
            document.getElementById('cepEdit').value = currentCep;
            document.getElementById('ufEdit').value = currentUf;
            document.getElementById('bairroEdit').value = currentBairro;
            document.getElementById('cidadeEdit').value = currentCidade;
            document.getElementById('numeroEdit').value = currentNumero;
            document.getElementById('ruaEdit').value = currentLogradouro;

            divEdit.style.display = 'block';
            overlay.style.display = 'block';

        } else if(event.target.classList.contains('imgDelete')) {
           currentid = event.target.getAttribute('data-id');
            divDelete.style.display = 'block';
            overlay.style.display = 'block';
            document.querySelector('.deleteMsg').innerHTML = `Deseja deletar a empresa de id ${currentid}?`;
        }
    });

    document.querySelector('.btnAdd').addEventListener('click', () => {
        divAdd.style.display = 'block';
        overlay.style.display = 'block';
    });

    document.querySelectorAll('.btnsCancel').forEach(btn => {
        btn.addEventListener('click', () => {
            overlay.style.display = 'none';
            divAdd.style.display = 'none';
            divEdit.style.display = 'none';
            divDelete.style.display = 'none';
        });
    });

    document.getElementById('confirmAddEmp').addEventListener('click', () => {
        const nomeFantasia = document.getElementById('nomeFa').value;
        const nomeSolicitante = document.getElementById('nomeSo').value;
        const telefoneSolicitante = document.getElementById('telSo').value;
        const razaoSocial = document.getElementById('razao').value;
        const cnpj = document.getElementById('CNPJ').value;
        const inscricaoSocial = document.getElementById('inscricao').value;
        const email = document.getElementById('email').value;
        const telefoneEmpresas = document.getElementById('telEmp').value;
        const ramo = document.getElementById('ramo').value;
        const porteEmpresas = document.getElementById('porte').value;
        const dataAlteracao = new Date();
        const numero = document.getElementById('numero').value;
        const cep = document.getElementById('cep').value;
        const logradouro = document.getElementById('rua').value;
        const cidade = document.getElementById('cidade').value;
        const bairro = document.getElementById('bairro').value;
        const uf = document.getElementById('uf').value;

       if(!nomeFantasia || !nomeSolicitante || !telefoneSolicitante || !razaoSocial || !cnpj || !inscricaoSocial || !email || !telefoneEmpresas || !ramo || !porteEmpresas) {
            toastAlert('Preencha todos os campos!', 'error');
            return;
       }

        const data = {
            nomeFantasia,
            nomeSolicitante,
            telefoneSolicitante,
            razaoSocial,
            cnpj,
            inscricaoSocial,
            email,
            telefoneEmpresas,
            ramo,
            endereco: {
                cep,
                numero,
                logradouro,
                cidade,
                bairro,
                uf
            },
            porteEmpresas,
            dataAlteracao
        };

        fetch(`${URL}/Empresa/Add`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Erro ao adicionar empresa');
                }
                return response.json();
            })
            .then(data => {
                toastAlert('Empresa adicionada com sucesso!', 'success');
                divAdd.style.display = 'none';
                overlay.style.display = 'none';
            })
            .catch(error => {
                const errorMessage = error.message ? error.message : 'Ocorreu um erro ao processar a solicitação';
                toastAlert(errorMessage, 'error');
            });
    });

    document.getElementById('confirmEditEmp').addEventListener('click', () => {
        const nomeFantasia = document.getElementById('nomeFaEdit').value;
        const nomeSolicitante = document.getElementById('nomeSoEdit').value;
        const telefoneSolicitante = document.getElementById('telSoEdit').value;
        const razaoSocial = document.getElementById('razaoEdit').value;
        const cnpj = document.getElementById('cnpjEdit').value;
        const inscricaoSocial = document.getElementById('inscricaoEdit').value;
        const email = document.getElementById('emailEdit').value;
        const telefoneEmpresas = document.getElementById('telEmpEdit').value;
        const ramo = document.getElementById('ramoEdit').value;
        const porteEmpresas = document.getElementById('porteEdit').value;
        const dataAlteracao = new Date();
        const numero = document.getElementById('numeroEdit').value;
        const cep = document.getElementById('cepEdit').value;
        const logradouro = document.getElementById('ruaEdit').value;
        const cidade = document.getElementById('cidadeEdit').value;
        const bairro = document.getElementById('bairroEdit').value;
        const uf = document.getElementById('ufEdit').value;

        const data = {
            nomeFantasia,
            nomeSolicitante,
            telefoneSolicitante,
            razaoSocial,
            cnpj,
            inscricaoSocial,
            email,
            telefoneEmpresas,
            ramo,
            endereco: {
                cep,
                numero,
                logradouro,
                cidade,
                bairro,
                uf
            },
            porteEmpresas,
            dataAlteracao
        };

        let id = parseInt(currentid);

        fetch(`${URL}/Empresa/Edit/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Erro ao editar empresa');
                }
                return response.json();
            })
            .then(data => {
                toastAlert('Empresa editada com sucesso!', 'success');
                currentPage = 0;
                nextDataPageEmpresas();
                divEdit.style.display = 'none';
                overlay.style.display = 'none';
            })
            .catch(error => {
                const errorMessage = error.message ? error.message : 'Ocorreu um erro ao processar a solicitação';
                toastAlert(errorMessage, 'error');
            });
    });

    document.getElementById('confirmDelete').addEventListener('click' , () => {
        const id = parseInt(currentid);
        fetch(`${URL}/Empresa/Delete/${id}`, {
            method: 'DELETE'
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro ao deletar empresa');
            }
            return response.text();
        })
        .then(data => {
            toastAlert('Empresa deletada com sucesso!', 'success');
            currentPage = 0;
            nextDataPageEmpresas();
            divDelete.style.display = 'none';
            overlay.style.display = 'none';
        })
        .catch(error => {
            const errorMessage = error.message ? error.message : 'Ocorreu um erro ao processar a solicitação';
            toastAlert(errorMessage, 'error');
        });
    });

    overlay.addEventListener('click', () => {
        overlay.style.display = 'none';
        divAdd.style.display = 'none';
        divEdit.style.display = 'none';
        divDelete.style.display = 'none';
    });
}

function addTableLinesEmpresas(data) {
    const table = document.querySelector('.tableEmp>tbody');
    const nextBtn = document.getElementById('nextBtnEmp');

    if (data.length === 0) {
        nextBtn.setAttribute('disabled', 'true');
        const newLine = document.createElement('tr');
        newLine.innerHTML = `<td class="thStyle" colspan="6">Nenhuma empresa cadastrada</td>`;
        table.appendChild(newLine);
        return;
    }
    nextBtn.removeAttribute('disabled');

    let count = 0;

    data.forEach((empresa, index) => {
        const newLine = document.createElement('tr');

        const classe = count % 2 === 0 ? 'azul' : '';
        count++;

        newLine.innerHTML = `
            <td class="thStyle thImg ${classe}">
                <img src="/icons//Cadastro-Empresa/Empresa.png" alt="Empresa" class="thImg">
            </td>
            <td class="thStyle ${classe}">${empresa.id}</td>
            <td class="thStyle ${classe}">${empresa.nomeFantasia}</td>
            <td class="thStyle ${classe}">${empresa.ramo}</td>
            <td class="thStyle ${classe}">${empresa.porteEmpresas}</td>
            <td class="thStyle ${classe}">
                <img src="/icons//Cadastro-Empresa/edit.png" 
                    data-id="${empresa.id}"
                    data-nomeFa="${empresa.nomeFantasia}"
                    data-nomeSo="${empresa.nomeSolicitante}"
                    data-telSo="${empresa.telefoneSolicitante}"
                    data-razao="${empresa.razaoSocial}"
                    data-cnpj="${empresa.cnpj}"
                    data-inscricao="${empresa.inscricaoSocial}"
                    data-email="${empresa.email}"
                    data-telEmp="${empresa.telefoneEmpresas}"
                    data-ramo="${empresa.ramo}"
                    data-porte="${empresa.porteEmpresas}"
                    data-cep="${empresa.endereco.cep}"
                    data-numero="${empresa.endereco.numero}"
                    data-logradouro="${empresa.endereco.logradouro}"
                    data-cidade="${empresa.endereco.cidade}"
                    data-bairro="${empresa.endereco.bairro}"
                    data-uf="${empresa.endereco.uf}"
                alt="Editar" class="imgEdit imgStyle">
                <img src="/icons//Cadastro-Empresa/delete.png"
                    data-id="${empresa.id}"
                alt="Deletar" class="imgDelete imgStyle">                
            </td>
        `;

        table.appendChild(newLine);
    });
}

function processEventEmpresas(event) {
    const currentid = event.target.getAttribute('data-id');
    const currentNomeFa = event.target.getAttribute('data-nomeFa');
    const currentNomeSo = event.target.getAttribute('data-nomeSo');
    const currentTelSo = event.target.getAttribute('data-telSo');
    const currentRazao = event.target.getAttribute('data-razao');
    const currentCnpj = event.target.getAttribute('data-cnpj');
    const currentInscricao = event.target.getAttribute('data-inscricao');
    const currentEmail = event.target.getAttribute('data-email');
    const currentTelEmp = event.target.getAttribute('data-telEmp');
    const currentRamo = event.target.getAttribute('data-ramo');
    const currentPorte = event.target.getAttribute('data-porte');
    const currentCep = event.target.getAttribute('data-cep');
    const currentNumero = event.target.getAttribute('data-numero');
    const currentLogradouro = event.target.getAttribute('data-logradouro');
    const currentCidade = event.target.getAttribute('data-cidade');
    const currentBairro = event.target.getAttribute('data-bairro');
    const currentUf = event.target.getAttribute('data-uf');

    return {
        currentid,
        currentNomeFa,
        currentNomeSo,
        currentTelSo,
        currentRazao,
        currentCnpj,
        currentInscricao,
        currentEmail,
        currentTelEmp,
        currentRamo,
        currentPorte,
        currentCep,
        currentNumero,
        currentLogradouro,
        currentCidade,
        currentBairro,
        currentUf
    };
}

function nextDataPageEmpresas () {
    const queryParams = new URLSearchParams();
    queryParams.append('page', currentPage);

    fetch(`${URL}/Empresa/search?${queryParams.toString()}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Erro ao buscar empresas');
        }
        return response.json();
    })
    .then(data => {
        const table = document.querySelector('.tableEmp>tbody');
        const trs = Array.from(table.children);
            trs.forEach(tag => {
                tag.parentNode.removeChild(tag);
            }
        );
        addTableLinesEmpresas(data);
    })
    .catch(error => {
        const errorMessage = error.message ? error.message : 'Ocorreu um erro ao processar a solicitação';
        toastAlert(errorMessage, 'error');
    });
}