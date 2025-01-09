const vendaEndpoint = "http://localhost:8080/venda/debito/cliente/";

function hideLoader() {
    document.getElementById("loading").style.display = "none";
}

async function getVendas(id) {
    let key = "Authorization";
    const response = await fetch(vendaEndpoint + id, {
        method: "GET",
        headers: new Headers({
            Authorization: localStorage.getItem(key),

        }),
    });

    if (response.status === 401 || response.status === 403) {
        localStorage.clear();
        window.top.location = "/view/login.html";
    }

    var data = await response.json();
    console.log(data);
    if (response) hideLoader();
    showDebitos(data);
}

async function getVendaPeriodo(startDate, endDate) {


    const vendaEndpoint1 = "http://localhost:8080/venda/debito/periodo";
    let key = "Authorization";
    const response = await fetch(vendaEndpoint1, {
        method: "POST",
        headers: new Headers({
            "Content-Type": "application/json; charset=utf8",
            Accept: "application/json",
            Authorization: localStorage.getItem(key),

        }),
        body: JSON.stringify({
            startDate: startDate,
            endDate: endDate,

        }),
    });

    var data = await response.json();
    console.log(data);
    if (response) hideLoader();
    showVendas(data);

}

document.addEventListener("DOMContentLoaded", function (event) {
    if (!localStorage.getItem("Authorization"))
        window.top.location = "/view/login.html";
});



function showVendas(vendas) {
    // Cria a tabela com cabeçalho
    let tableContent = `
        <table class="table">
            <thead>
                <tr>
                    <th scope="col">ID da Venda</th>
                    <th scope="col">Cliente</th>
                    <th scope="col">Produto</th>
                    <th scope="col">Quantidade</th>
                    <th scope="col">Valor Pago</th>
                    <th scope="col">Forma de Pagamento</th>
                    <th scope="col">Vendedor</th>
                    <th scope="col">Data</th>
                </tr>
            </thead>
            <tbody>
    `;

    // Itera sobre cada venda no JSON
    vendas.forEach(venda => {
        const { id, cliente, produto, valorCliente, formPag, user, data, quantidade } = venda;

        // Itera sobre os produtos e exibe cada um
        produto.forEach((prod, index) => {
            tableContent += `
                <tr>
                    <td>${id}</td>
                    <td>${cliente.nome}</td>
                    <td>${prod.modelo} (${prod.cor}, ${prod.tamanho})</td>
                    <td>${quantidade[index]}</td>
                    <td>R$ ${valorCliente.toFixed(2)}</td>
                    <td>${formPag}</td>
                    <td>${user.nome}</td>
                    <td>${new Date(data).toLocaleDateString('pt-BR')}</td>
                </tr>
            `;
        });
    });

    // Fecha a tabela
    tableContent += `
            </tbody>
        </table>
    `;

    // Insere a tabela no frontend
    document.getElementById("debitos").innerHTML = tableContent;
}


function showDebitos(vendas) {
    if (!vendas || vendas.length === 0) {
        document.getElementById("debitos").innerHTML = "<p>Não há débitos para exibir.</p>";
        return;
    }

    // Pegar o valor total de débito (como todos os objetos têm o mesmo totalDebito, usamos o primeiro)
    const totalDebito = vendas[0].totalDebito;

    // Criar o cabeçalho da tabela
    let tab = `<thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">CLIENTE</th>
                    <th scope="col">PRODUTO</th>
                    <th scope="col">QUANTIDADE</th>
                    <th scope="col">VALOR PAGO</th>
                    <th scope="col">FORMA DE PAGAMENTO</th>
                    <th scope="col">VENDEDOR</th>
                    <th scope="col">DATA</th>
                </tr>
               </thead>`;

    // Construir a tabela para as vendas
    vendas.forEach((vendaObj) => {
        const { venda } = vendaObj;
        const { id, cliente, produto, quantidade, valorCliente, formPag, user, data } = venda;

        // Iterar sobre os produtos da venda
        produto.forEach((prod, index) => {
            tab += `
                <tr>
                    <td>${id}</td>
                    <td>${cliente?.nome || "Não informado"}</td>
                    <td>
                        ${prod.modelo} (${prod.cor}, ${prod.tamanho})
                    </td>
                    <td>${quantidade[index] || "Quantidade não informada"}</td>
                    <td>R$ ${valorCliente.toFixed(2)}</td>
                    <td>${formPag}</td>
                    <td>${user?.nome || "Não informado"}</td>
                    <td>${data || "Data não informada"}</td>
                </tr>`;
        });
    });

    // Exibir o valor total e a tabela
    document.getElementById("debitos").innerHTML = `
        <h3>Valor Total de Débito: R$${totalDebito.toFixed(2)}</h3>
        <table class="table">
            ${tab}
        </table>
    `;
}




