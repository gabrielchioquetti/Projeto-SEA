// =========================
// CALENDÁRIO
// =========================

const daysContainer = document.getElementById("days");
const monthYearText = document.getElementById("month-year");
const prevBtn = document.getElementById("prev");
const nextBtn = document.getElementById("next");

if (daysContainer && monthYearText && prevBtn && nextBtn) {

    let date = new Date();

    function renderCalendar() {

        daysContainer.innerHTML = "";

        const month = date.getMonth();
        const year = date.getFullYear();

        monthYearText.innerText =
            new Intl.DateTimeFormat("pt-BR", {
                month: "long",
                year: "numeric"
            }).format(date);

        const firstDayIndex =
            new Date(year, month, 1).getDay();

        const lastDay =
            new Date(year, month + 1, 0).getDate();

        // Espaços vazios
        for (let x = firstDayIndex; x > 0; x--) {
            daysContainer.innerHTML += `<div></div>`;
        }

        // Dias
        for (let i = 1; i <= lastDay; i++) {

            const isToday =
                i === new Date().getDate() &&
                month === new Date().getMonth() &&
                year === new Date().getFullYear()
                    ? "today"
                    : "";

            daysContainer.innerHTML += `
                <div class="${isToday}">
                    ${i}
                </div>
            `;
        }
    }

    prevBtn.addEventListener("click", () => {
        date.setMonth(date.getMonth() - 1);
        renderCalendar();
    });

    nextBtn.addEventListener("click", () => {
        date.setMonth(date.getMonth() + 1);
        renderCalendar();
    });

    renderCalendar();
}


// =========================
// MENU
// =========================

document.addEventListener("DOMContentLoaded", () => {

    const btnMenu = document.getElementById("btn-menu");
    const menuItems = document.querySelectorAll(".menu-item");

    if (!btnMenu || menuItems.length === 0) {
        return;
    }

    // Mostrar inicialmente
    menuItems.forEach((item, index) => {

        setTimeout(() => {
            item.classList.add("show");
        }, index * 100);

    });

    // Toggle menu
    btnMenu.addEventListener("click", (e) => {

        e.preventDefault();

        menuItems.forEach((item) => {
            item.classList.toggle("show");
        });

    });

});