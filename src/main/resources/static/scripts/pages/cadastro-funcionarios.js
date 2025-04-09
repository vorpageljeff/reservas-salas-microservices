let currentPageFuncionario = 0;
function onOpenFuncionario() {
    const priorBtn = document.getElementById('priorBtnFunc');
    const nextBtn = document.getElementById('nextBtnFunc');
    const overlay = document.querySelector('.overlay');
    const divAdd = document.querySelector('.divAddFunc');
    const divEdit = document.querySelector('.divEditFunc');
    const divDelete = document.querySelector('.divDeleteFunc');
    let currentId;

    nextDataPageFuncionarios()

    nextBtn.addEventListener('click', () => {
        currentPageFuncionario++;
        nextDataPageFuncionarios();
    });

    priorBtn.addEventListener('click', () => {
        currentPageFuncionario--;
        if (currentPageFuncionario < 0) {
            currentPageFuncionario = 0;
        }
        nextDataPageFuncionarios();
    });

    document.getElementById('confirmDelete').addEventListener('click', () => {
        const id = parseInt(currentId);
        fetch(`${URL}/Funcionario/Delete/${id}`, {
            method: 'DELETE'
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro ao deletar Funcionario');
            }
            return response.text();
        })
        .then(data => {
            toastAlert('Funcionario deletado com sucesso!', 'success');
            currentPageFuncionario = 0;
            nextDataPageFuncionarios();
            console.log(divDelete.style.display);
            console.log(overlay.style.display);
            divDelete.style.display = 'none';
            overlay.style.display = 'none';
            console.log(divDelete.style.display);
            console.log(overlay.style.display);
        })
        .catch(error => {
            const errorMessage = error.message ? error.message : 'Ocorreu um erro ao processar a solicitação';
            toastAlert(errorMessage, 'error');
        });
    })

    document.querySelector('.tableFunc').addEventListener('click', (event) => {

        if (event.target.classList.contains('imgEdit')) {
            const {
                currentNome, currentCpf,
                currentDataNasc, currentLogin, currentCargo,
                currentEmail
            } = processEventFuncionarios(event);
            currentId = event.target.getAttribute('data-id');
            divEdit.style.display = 'block';
            overlay.style.display = 'block';
            document.getElementById('nomeEdit').value = currentNome;
            document.getElementById('cpfEdit').value = currentCpf;
            document.getElementById('dataNascEdit').value = currentDataNasc;
            document.getElementById('loginEdit').value = currentLogin;
            document.getElementById('cargoEdit').value = currentCargo;
            document.getElementById('emailEdit').value = currentEmail;
        } else if (event.target.classList.contains('imgDelete')) {
            currentId = event.target.getAttribute('data-id');
            divDelete.style.display = 'block';
            overlay.style.display = 'block';
            document.querySelector('.deleteMsg').innerHTML = `Deseja deletar o funcionário de id ${currentId}?`;
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

    overlay.addEventListener('click', () => {
        overlay.style.display = 'none';
        divAdd.style.display = 'none';
        divEdit.style.display = 'none';
        divDelete.style.display = 'none';
    });

    document.getElementById('confirmAdd').addEventListener('click', () => {
        const nome = document.getElementById('nomeAdd').value;
        const cpf = document.getElementById('cpfAdd').value;
        const dataNascimento = document.getElementById('dataNascAdd').value;
        const login = document.getElementById('loginAdd').value;
        const cargo = document.getElementById('cargoAdd').value;
        const email = document.getElementById('emailAdd').value;
        const password = document.getElementById('senhaAdd').value;

        if (nome === '' || cpf === '' || dataNascimento === '' || login === '' || cargo === '' || email === '' || password === '') {
            toastAlert('Preencha todos os campos!', 'error');
            return
        }

        const data = {
            nome,
            cpf,
            email,
            dataNascimento,
            usuario: {
                login,
                password,
                isAdmin: cargo === 'Administrador' ? true : false
            },
            cargo,
        };

        fetch(`${URL}/Funcionario/Add`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Erro ao cadastrar funcionário');
                }
                return response.json();
            })
            .then(data => {
                toastAlert("Funcionario cadastrado com sucesso!", "success");
                divAdd.style.display = 'none';
                overlay.style.display = 'none';
                currentPageFuncionario = 0;
                nextDataPageFuncionarios();
            })
            .catch(error => {
                toastAlert("Erro ao cadastrar funcionário!", "error");
            });
    });

    document.getElementById('confirmEdit').addEventListener('click', () => {
        const nome = document.getElementById('nomeEdit').value;
        const cpf = document.getElementById('cpfEdit').value;
        const dataNascimento = document.getElementById('dataNascEdit').value;
        const login = document.getElementById('loginEdit').value;
        const cargo = document.getElementById('cargoEdit').value;
        const email = document.getElementById('emailEdit').value;

        const data = {
            nome,
            cpf,
            dataNascimento,
            login,
            cargo,
            email,
        };

        let id = parseInt(currentId);
        fetch(`${URL}/Funcionario/Edit/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Erro ao editar funcionário!');
                }
                return response.json();
            
            })
            .then(data => {
                toastAlert('Funcionário editado com sucesso!', 'success');
                divEdit.style.display = 'none';
                overlay.style.display = 'none';
                currentPageFuncionario = 0;
                nextDataPageFuncionarios();
            })
            .catch(error => {
                toastAlert('Erro ao editar funcionário!', 'error');
            });
    });
}

// function disableBtns(data) {
//     priorBtn.disabled = (currentPageFuncionario <= 1);
//     nextBtn.disabled = (currentPageFuncionario >= data);
// }

// function updateCheckbox(option) {
//     var checkboxes = document.getElementsByName("genero");
//     for (var i = 0; i < checkboxes.length; i++) {
//         if (checkboxes[i].value === option) {
//             checkboxes[i].checked = true;
//         } else {
//             checkboxes[i].checked = false;
//         }
//     }
// }

function addTableLinesFuncionarios(data) {
    const nextBtn = document.getElementById('nextBtnFunc');
    const table = document.querySelector('.tableFunc>tbody');

    if (data.length === 0) {
        nextBtn.setAttribute('disabled', 'true');
        const newLine = document.createElement('tr');
        newLine.innerHTML = `<td class="thStyle" colspan="5">Nenhum funcionário cadastrado</td>`;
        table.appendChild(newLine);
        return;
    }
    nextBtn.removeAttribute('disabled');
    let count = 0;

    data.forEach(funcionario => {
        const newLine = document.createElement('tr');

        const classe = count % 2 === 0 ? 'azul' : '';
        count++;

        newLine.innerHTML = `
            <td class="thStyle imgFunc ${classe}">
                <img src="/icons/Cadastro-Funcionario/Businessman.png" alt="Funcionario" class="imgFunc">
            </td>
            <td class="thStyle ${classe}">${funcionario.id}</td>
            <td class="thStyle ${classe}">${funcionario.nome}</td>
            <td class="thStyle ${classe}">${funcionario.cargo.descricao}</td>
            <td class="thStyle ${classe}">
                <img src="/icons/Cadastro-Funcionario/edit.png" 
                    data-id="${funcionario.id}"
                    data-nome="${funcionario.nome}"
                    data-cpf="${funcionario.cpf}"
                    data-nasc="${funcionario.dataNascimento}"
                    data-cargo="${funcionario.cargo.descricao}"
                    data-email="${funcionario.email}"
                    data-login="${funcionario.usuario.login}"
                alt="Editar" class="imgEdit imgStyle">
                <img src="/icons/Cadastro-Funcionario/delete.png"
                    data-id="${funcionario.id}"
                alt="Deletar" class="imgDelete imgStyle">                
            </td>
        `;

        table.appendChild(newLine);
    });
}

function processEventFuncionarios(event) {
    const currentId = event.target.getAttribute('data-id');
    const currentNome = event.target.getAttribute('data-nome');
    const currentCpf = event.target.getAttribute('data-cpf');
    const currentDataNasc = event.target.getAttribute('data-nasc');
    const currentLogin = event.target.getAttribute('data-login');
    const currentCargo = event.target.getAttribute('data-cargo');
    const currentEmail = event.target.getAttribute('data-email');

    return {
        currentId,
        currentNome,
        currentCpf,
        currentDataNasc,
        currentLogin,
        currentCargo,
        currentEmail,
    };
}

function nextDataPageFuncionarios() {
    const queryParams = new URLSearchParams();
    queryParams.append('page', currentPageFuncionario);

    fetch(`${URL}/Funcionario/search?${queryParams.toString()}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro ao buscar os dados!');
            }
            return response.json();
        })
        .then(data => {
            const table = document.querySelector('.tableFunc>tbody');
            const trs = Array.from(table.children);
                trs.forEach(tag => {
                    tag.parentNode.removeChild(tag);
                }
            );
            addTableLinesFuncionarios(data);
        })
        .catch(error => console.error('Erro ao buscar os dados:', error));
}

    // document.querySelector('.imgSearch').addEventListener('click', searchData);

    // document.getElementById('search').addEventListener('keydown', (event) => {
    //     if (event.key === 'Enter') {
    //         searchData();
    //     }
    // });

    // document.getElementById('search').addEventListener('input', (event) => {
    //     if (event.target.value === '') {
    //         nextDataPageFuncionarios();
    //     }
    // });

// function searchData() {
//     const type = document.getElementById('select').value;
//     const search = document.getElementById('search').value;
//
//     const urlSearch = `/funcionarios/search`;
//     const body = { type, search }
//
//     fetch(urlSearch, {
//         method: 'POST',
//         headers: {
//             'Content-Type': 'application/json'
//         },
//         body: JSON.stringify(body)
//     })
//         .then(response => response.json())
//         .then(data => {
//             addTableLinesFuncionarios(data);
//             disableBtns(data.totalPages);
//         })
//         .catch(error => {
//             console.error('Erro ao carregar dados!', error);
//         });
// }