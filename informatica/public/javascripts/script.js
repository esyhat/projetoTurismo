form.addEventListener("submit", function(event) {
    event.preventDefault(); // Evita o envio padrão do formulário

    // Captura os dados do formulário
    let nome = document.querySelector("#name").value;
    let email = document.querySelector("#email").value;
    let senha = document.querySelector("#senha").value;

    // Validação simples
    if (!nome || !email || !senha) {
        alert("Por favor, preencha todos os campos.");
        return;
    }

    let dados = {
        nome: nome,
        email: email,
        senha: senha,
        id: document.querySelector("input[name='usuario.id']").value // Captura o ID se existir
    };

    // Envia os dados para o servidor
    fetch('@{Usuarios.salvarr}', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(dados)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(data => {
        console.log('Success:', data);
        
        // Lógica para lidar com a resposta do servidor
        if (data.success) {
            alert('Usuário salvo com sucesso!'); // Mensagem de sucesso
            window.location.href = '@{Usuarios.listar}'; // Redireciona para a listagem
        } else {
            alert('Erro: ' + data.message); // Mensagem de erro
        }
    })
    .catch((error) => {
        console.error('Error:', error);
        alert('Ocorreu um erro ao salvar o usuário.'); // Mensagem de erro
    });
});