class Animal(
    var animalType: String?,
    var name: String?,
    var legCount: Int,
    var isPredator: Boolean,
    var color: String?,
    var habitat: String?
) {

    override fun toString(): String {
        return "Тип: $animalType, Имя: $name, Кол-во ног: $legCount, Хищник: $isPredator, Цвет: $color, Ареал обитания: $habitat)"
    }
}







