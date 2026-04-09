// Calendário

const daysContainer = document.getElementById("days");
const monthYearText = document.getElementById("month-year");
let date = new Date();

function renderCalendar() {
    daysContainer.innerHTML = "";
    const month = date.getMonth();
    const year = date.getFullYear();

    monthYearText.innerText = new Intl.DateTimeFormat('pt-BR', { month: 'long', year: 'numeric' }).format(date);

    const firstDayIndex = new Date(year, month, 1).getDay();
    const lastDay = new Date(year, month + 1, 0).getDate();

    // Espaços vazios antes do primeiro dia do mês
    for (let x = firstDayIndex; x > 0; x--) {
        daysContainer.innerHTML += `<div></div>`;
    }

    // Dias do mês
    for (let i = 1; i <= lastDay; i++) {
        const isToday = i === new Date().getDate() && month === new Date().getMonth() && year === new Date().getFullYear() ? "today" : "";
        daysContainer.innerHTML += `<div class="${isToday}">${i}</div>`;
    }
}

document.getElementById("prev").onclick = () => { date.setMonth(date.getMonth() - 1); renderCalendar(); };
document.getElementById("next").onclick = () => { date.setMonth(date.getMonth() + 1); renderCalendar(); };

renderCalendar();

// Menu dinâmico

document.addEventListener("DOMContentLoaded", () => {
    const btnMenu = document.getElementById("btn-menu");
    const menuItems = document.querySelectorAll(".menu-item");

    // Mostra os itens inicialmente com a animação que já tínhamos
    menuItems.forEach((item, index) => {
        setTimeout(() => {
            item.classList.add("show");
        }, index * 100);
    });

    // Função para esconder/mostrar ao clicar no ícone
    btnMenu.addEventListener("click", (e) => {
        e.preventDefault(); // Evita que o link recarregue a página
        
        menuItems.forEach((item) => {
            // Se tiver a classe 'show', remove (some), se não tiver, adiciona (aparece)
            item.classList.toggle("show");
        });
    });
});

