// Escolher disciplinas por área
const areaSelect = document.getElementById("area");
const listaDisciplinas = document.getElementById("lista-disciplinas");

if (areaSelect && listaDisciplinas) {

    areaSelect.addEventListener("change", async () => {

        const area = areaSelect.value;

        listaDisciplinas.innerHTML = "";

        if (!area) return;

        try {

            const response = await fetch(`/disciplinas/area?area=${area}`);
            const disciplinas = await response.json();

            disciplinas.forEach(d => {

                listaDisciplinas.innerHTML += `
                    <div>
                        <input 
                            type="checkbox"
                            name="disciplinas"
                            value="${d.idDisciplina}"
                            id="disc_${d.idDisciplina}"
                        >

                        <label for="disc_${d.idDisciplina}">
                            ${d.nome}
                        </label>
                    </div>
                `;
            });

        } catch (erro) {
            console.error("Erro ao buscar disciplinas", erro);
        }

    });

}