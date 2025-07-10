    // Script para actualizar factura en vivo
    document.getElementById('inputFactura').addEventListener('input', e => {
        document.getElementById('facturaNumero').textContent = e.target.value || '---';
    });

    document.getElementById('inputCedula').addEventListener('input', e => {
        document.getElementById('facturaCedula').textContent = e.target.value || '---';
    });

    document.getElementById('inputMonto').addEventListener('input', e => {
        document.getElementById('facturaMonto').textContent = e.target.value || '---';
    });

    document.getElementById('inputFecha').addEventListener('input', e => {
        document.getElementById('facturaFecha').textContent = e.target.value || '---';
    });

    document.getElementById('inputDescripcion').addEventListener('input', e => {
        document.getElementById('facturaDescripcion').textContent = e.target.value || '---';
    });

    document.getElementById('inputEstado').addEventListener('change', e => {
        document.getElementById('facturaEstado').textContent = e.target.value || '---';
    });

    // PDF básico
    async function descargarPDF() {
        const { jsPDF } = window.jspdf;
        const doc = new jsPDF();

        doc.text("Factura", 105, 20, { align: "center" });
        doc.text("Número de Factura: " + document.getElementById('facturaNumero').textContent, 20, 40);
        doc.text("Fecha: " + document.getElementById('facturaFecha').textContent, 20, 50);
        doc.text("Cédula del Cliente: " + document.getElementById('facturaCedula').textContent, 20, 60);
        doc.text("Descripción: " + document.getElementById('facturaDescripcion').textContent, 20, 70);
        doc.text("Monto: " + document.getElementById('facturaMonto').textContent, 20, 80);
        doc.text("Estado: " + document.getElementById('facturaEstado').textContent, 20, 90);

        doc.save("factura.pdf");
    }

