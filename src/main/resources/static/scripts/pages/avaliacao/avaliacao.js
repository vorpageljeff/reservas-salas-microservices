const questionNumbers = 10;

const addFile = () => {
    alert('W.I.P');
}

const renderQuestion = (question) => {
    const form = document.querySelector('.form-avaliacao');
    const div = document.createElement('div');
    const FwhatsChecked = () => {
        const randomNumber = Math.random();

        if (randomNumber < 0.4) {
            return 1; // Retorna 1 com 40% de probabilidade
        } else if (randomNumber < 0.8) {
            return -1; // Retorna -1 com 40% de probabilidade
        } else {
            return 0; // Retorna 0 com 20% de probabilidade
        }
    }
    const whatsChecked = FwhatsChecked();
    div.classList.add('form-question');
    div.innerHTML = `
        <h2>${question.descricao}</h2>
        <div class="form-answer">
            <label>
                <input type="radio" name="answer-${question.id}" value="Conforme" ${enviornment === 'dev' && whatsChecked === 1 ? 'checked' : null}>
                <p>Conforme</p>
            </label>
            <br>
            <label>
                <input type="radio" name="answer-${question.id}" value="NaoConforme" ${enviornment === 'dev' && whatsChecked === -1 ? 'checked' : null}>
                <p>Não conforme</p>
            </label>
            <br>
            <label>
                <input type="radio" name="answer-${question.id}" value="NaoSeAdequa" ${enviornment === 'dev' && whatsChecked === 0 ? 'checked' : null}>
                <p>Não aplicavel</p>
            </label>
            <!-- <div class="add-file">
                <button class="btn-file" onClick="addFile()">Adicionar Arquivo</button>
            </div> -->
        </div>
    `;
    return form.appendChild(div);
}

const isAllQuestionsAnswered = (allQuestions) => {

    for (let i = 0; i < allQuestions.length; i++) {
        const answer = document.querySelector(`input[name="answer-${allQuestions[i].id}"]:checked`);
        if (!answer) {
            return false;
        }
    }

    return true;
};

const onOpenAvaliacao = (props) => {
    const form = document.querySelector('.form-avaliacao')
    form.addEventListener('submit', (event) => {
        if (event.explicitOriginalTarget !== document.getElementById("btn-submit")) {
            event.preventDefault();
        }
    });
    const governamental = [];
    const ambiental = [];
    const social = [];

    fetch(`${URL}/questionario`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro ao recuperar dados');
            }
            return response.json();
        })
        .then(data => {
            data.forEach(item => {
                if (item.eixo === 'Governamental') {
                    if (governamental.length < questionNumbers) {
                        governamental.push(item);
                    }
                } else if (item.eixo === 'Ambiental') {
                    if (ambiental.length < questionNumbers) {
                        ambiental.push(item);
                    }    
                } else if (item.eixo === 'Social') {
                    if (social.length < questionNumbers) {
                        social.push(item);
                    }
                }
            });

            governamental.forEach(item => renderQuestion(item));

            ambiental.forEach(item => renderQuestion(item));

            social.forEach(item => renderQuestion(item));

            const allQuestions = [...governamental, ...ambiental, ...social];
            
            const btnSubmit = document.createElement('button');
            btnSubmit.id = 'btn-submit';
            btnSubmit.classList.add('btn-submit');
            btnSubmit.textContent = 'Finalizar';
            btnSubmit.addEventListener('click', () => {
                if (isAllQuestionsAnswered(allQuestions)) {
                    const questions = [];
                    allQuestions.forEach(question => {
                        const answer = document.querySelector(`input[name="answer-${question.id}"]:checked`);
                        questions.push(
                            {
                                perguntaId: question.id,
                                respostaUsuario: answer.value,
                                perguntaEixo: question.eixo,
                            }
                        );
                    });
                    const fullURL = `${URL}/processarRespostas?empresa_id=${props.id}`;
                    const body = questions
                    fetch(fullURL, {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                        },
                        body: JSON.stringify(body)
                    }).then(response => {
                        if (!response.ok) {
                            throw new Error('Erro ao enviar dados');
                        }
                        return response.json();
                    }).then(data => {
                        toastAlert('Respostas enviadas com sucesso', 'success');
                        getMainFrameContent('result-avaliacao', data);
                    }).catch(() => {
                        toastAlert('Erro ao enviar dados', 'error');
                    });
                } else {
                    toastAlert('Por favor, responda todas as perguntas antes de prosseguir', 'error');
                }
            });
            form.appendChild(btnSubmit);
        })
        .catch(err => {
            console.error(err);
        });
};