let isDropdownOpened = false;

let settedCompany = null;

const fetchEmpresas = (input, dropdown) => {
    const filter = input.value.toLowerCase();
    const queryParams = new URLSearchParams();
    queryParams.append('nomeFantasia', filter ? filter : 'a')
    const fullUrl = `${URL}/Empresa/search?${queryParams.toString()}`;
    fetch(fullUrl)
        .then (response => {
            if (!response.ok) {
                throw new Error('Erro ao buscar empresas');
            }
            return response.json();
        })
        .then (response => {
            dropdown.innerHTML = '';
            response.forEach(empresa => {
                const li = document.createElement('li');
                li.textContent = empresa.nomeFantasia;
                li.addEventListener('click', () => {
                    input.value = empresa.nomeFantasia;
                    settedCompany = empresa
                    dropdown.innerHTML = '';
                });
                dropdown.appendChild(li);
            });
        });
}

function clearSelectedCompanyIfNotMatch(input) {
    const inputValue = input.value.toLowerCase();
    if (settedCompany && settedCompany.nomeFantasia.toLowerCase() !== inputValue) {
        settedCompany = null;
    }
}

const onOpenSelecaoEmpresa = () => {
    const input = document.querySelector('.search');
    const dropdown = document.querySelector('.dropdown-content');
    const content = document.querySelector('.content-container');

    input.addEventListener('input', () => {
        fetchEmpresas(input, dropdown)
        isDropdownOpened = true;
        clearSelectedCompanyIfNotMatch(input);
    });

    input.addEventListener('focus', () => {
        if (!isDropdownOpened) {
            fetchEmpresas(input, dropdown);
            isDropdownOpened = true;
            clearSelectedCompanyIfNotMatch(input);
        }
    });

    input.addEventListener('keydown', function(event) {
        if (event.key === 'Enter') {
            const selected = dropdown.querySelector('li');
            if (selected) {
                input.value = selected.textContent;
                dropdown.innerHTML = '';
                isDropdownOpened = false;
                clearSelectedCompanyIfNotMatch(input);
            }
        }
    });

    content.addEventListener('click', function(event) {
        if (!input.contains(event.target) && !dropdown.contains(event.target)) {
            dropdown.innerHTML = '';
            isDropdownOpened = false;
            clearSelectedCompanyIfNotMatch(input);
        }
    });

    const btnNext = document.querySelector('.btn-next');
    btnNext.addEventListener('click', () => {
        if (settedCompany) {
            getMainFrameContent('avaliacao', settedCompany);
            settedCompany = null;
        } else {
            toastAlert('Por favor, selecione uma empresa antes de avançar', 'error');
        }
    });
}
