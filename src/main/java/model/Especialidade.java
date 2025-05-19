package model;

public enum Especialidade {
    CARDIOLOGIA("Cardiologia"),
    DERMATOLOGIA("Dermatologia"),
    GINECOLOGIA("Ginecologia"),
    PEDIATRIA("Pediatria"),
    ORTOPEDIA("Ortopedia"),
    NEUROLOGIA("Neurologia"),
    OFTALMOLOGIA("Oftalmologia"),
    UROLOGIA("Urologia"),
    ENDOCRINOLOGIA("Endocrinologia"),
    GASTROENTEROLOGIA("Gastroenterologia");

    private final String nome;

    Especialidade(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return nome;
    }
}
