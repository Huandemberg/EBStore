const vendaEndpoint = "http://localhost:8080/venda/vendas";

function hideLoader() {
    document.getElementById("loading").style.display = "none";
}

function show(vendas) {
    let tab = `<thead>
                <th scope="col">#</th>
                <th scope="col">CLIENTE</th>
                <th scope="col">PRODUTO</th>
                <th scope="col">VALOR_CLIENTE</th>
                <th scope="col">FORMPAG</th>
                <th scope="col">VENDEDOR</th>
               </thead>`;
    
    for (let venda of vendas) {
        tab+= `
                <tr>
                    <td scope="row">${venda.id}</td>
                    <td>${venda.cliente.nome}</td>
                    <td>${venda.produto.modelo}</td>
                    <td>${venda.valorCliente}</td>
                    <td>${venda.formPag}</td>
                    <td>${venda.user.nome}</td>
                </tr> `;
    }

    document.getElementById("vendas").innerHTML = tab;
}

async function getVendas() {
    let key = "Authorization";
    const response = await fetch(vendaEndpoint, {
        method: "GET",
        headers: new Headers({
            Authorization: localStorage.getItem(key),

        }),
    });
    
    var data = await response.json();
    console.log(data);
    if(response) hideLoader();
    show(data);
}

document.addEventListener("DOMContentLoaded", function (event) {
    if (!localStorage.getItem("Authorization"))
      window.location = "/view/login.html";
  });

getVendas();