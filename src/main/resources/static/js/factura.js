    async function descargarPDF() {
        const { jsPDF } = window.jspdf;
        const doc = new jsPDF();

        const numeroFactura = document.getElementById('inputFactura').value || '---';
        const cedula = document.getElementById('inputCedula').value || '---';
        const monto = document.getElementById('inputMonto').value || '---';
        const fecha = document.getElementById('inputFecha').value || '---';
        const descripcion = document.getElementById('inputDescripcion').value || '---';
        const estado = document.getElementById('inputEstado').value || '---';

        doc.text("Factura", 105, 20, { align: "center" });
        doc.text("Número de Factura: " + numeroFactura, 20, 40);
        doc.text("Fecha: " + fecha, 20, 50);
        doc.text("Cédula del Cliente: " + cedula, 20, 60);
        doc.text("Descripción: " + descripcion, 20, 70);
        doc.text("Monto: " + monto, 20, 80);
        doc.text("Estado: " + estado, 20, 90);

        doc.save("factura.pdf");
    }

