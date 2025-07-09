function toggleBN() {
    const btn = document.getElementById('bn-toggle');
    const estado = document.getElementById('modo-estado');

    btn.classList.toggle('on');
    btn.classList.toggle('off');

    if (btn.classList.contains('off')) {
        document.body.classList.add('modo-blanco-negro');
    } else {
        estado.innerText = 'on';
        document.body.classList.remove('modo-blanco-negro');
    }
}

// Estilo opcional para blanco y negro
const estiloBN = document.createElement('style');
estiloBN.innerHTML = `
    .modo-blanco-negro {
        filter: grayscale(100%) !important;
    }
`;
document.head.appendChild(estiloBN);




