import java.io.File
import java.io.FileWriter
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.*

    fun loadAnimalsFromFile(filename: String): MutableList<Animal> {
        val animals = mutableListOf<Animal>()
        try {
            FileInputStream(filename).use { fileInputStream ->
                InputStreamReader(fileInputStream, Charsets.UTF_8).use { inputStreamReader ->
                    BufferedReader(inputStreamReader).use { reader ->
                        reader.forEachLine { line ->
                            val parts = line.split(";")
                            if (parts.size == 6) {
                                val animal = Animal(
                                    parts[0].trim(),
                                    parts[1].trim(),
                                    parts[2].trim().toInt(),
                                    parts[3].trim().toBoolean(),
                                    parts[4].trim(),
                                    parts[5].trim()
                                )
                                animals.add(animal)
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            println("Ошибка при загрузке данных из файла: ${e.message}")
        }
        return animals
    }

    fun saveAnimalsToFile(filename: String, animals: List<Animal>) {
        try {
            val file = File(filename)
            file.writeText("")
            FileWriter(file, true).use { writer ->
                for (animal in animals) {
                    writer.write("${animal.animalType};${animal.name};${animal.legCount};${animal.isPredator};${animal.color};${animal.habitat}\n")
                }
            }
        } catch (e: Exception) {
            println("Ошибка при сохранении данных в файл: ${e.message}")
        }
    }

    val filename = "C:\\Users\\А\\IdeaProjects\\zoo_Kotlin\\src\\main\\resources\\animals.txt"

//хз, но видит файл только по абсолютному пути, пробовал по разному

    //val projectDir = System.getProperty("user.dir")
    //val relativePath = "zoo_Kotlin\\scr\\main\\resources\\animals.txt"
    //val filename = "$projectDir\\$relativePath"

    val animals = loadAnimalsFromFile(filename)

    fun printAllAnimals(animals: MutableList<Animal>) {
        for (animal in animals) {
            println(animal)
        }
    }

    fun existName(name: String): Boolean {
        var result = false
        for (animal in animals) {
            if (animal.name == name) {
                result = true
                break
            }
        }
        return result
    }

    fun changeName(name: String) {
        for (animal in animals) {
            if (animal.name == name) {
                println("Введите новое имя")
                val input = readlnOrNull()?.trim()
                if (input != null) {
                    if (!existName(input)){     //проверка есть ли уже такое имя
                        animal.name = name
                        saveAnimalsToFile(filename, animals)
                        println("Имя успешно изменено\n")
                    }
                    else {
                        print("Животное с таким именем уже существует!")
                    }
                }
            }
        }
    }

    fun <Animal> MutableList<Animal>.addAnimal(element: Animal) {
        add(element)
    }

    fun MutableList<Animal>.removeByName(name: String) {
        val filteredList = filter { it.name != name }.toMutableList()
        clear()
        addAll(filteredList)
    }

fun main() {

    while (true) {
        println("Список доступных команд:\nвывести\nвывести по признаку\nизменить имя\nдобавить\nудалить по имени\nвыход\n")
        println("Введите команду:")
        val input = readlnOrNull()
        when (input?.trim()) {
            "вывести" -> {
                printAllAnimals(animals)
            }

            "изменить имя" -> {
                println("Введите имя животного")
                val input = readlnOrNull()?.trim()
                if (input != null) {
                    changeName(input)
                }
            }

            "вывести по признаку" -> {
                while (true) {
                    println("Доступные признаки:\nтип\nимя\nколичество ног\nхищник\nцвет\nареал обитания\nназад\n")
                    println("Введите признак:")
                    val input = readlnOrNull()
                    when (input?.trim()) {
                        "тип" -> {
                            println("Введите тип:")
                            val input = readlnOrNull()
                            for (animal in animals) {
                                if (animal.animalType == input) {
                                    println(animal)
                                }
                            }

                        }

                        "имя" -> {
                            println("Введите имя:")
                            val input = readlnOrNull()
                            for (animal in animals) {
                                if (animal.name == input) {
                                    println(animal)

                                }
                            }
                        }

                        "количество ног" -> {
                            println("Введите количество ног:")
                            val input = readlnOrNull()
                            for (animal in animals) {
                                if (input != null) {
                                    if (animal.legCount == input.toInt()) {
                                        println(animal)
                                    }
                                }
                            }
                        }

                        "хищник" -> {
                            println("Введите хищник ли(True/False):")
                            val input = readlnOrNull()
                            for (animal in animals) {
                                if (animal.isPredator == input.toBoolean()) {
                                    println(animal)
                                }
                            }
                        }

                        "цвет" -> {
                            println("Введите цвет:")
                            val input = readlnOrNull()
                            for (animal in animals) {
                                if (animal.color == input) {
                                    println(animal)
                                }
                            }
                        }

                        "ареал обитания" -> {
                            println("Введите ареал обитания:")
                            val input = readlnOrNull()
                            for (animal in animals) {
                                if (animal.habitat == input) {
                                    println(animal)
                                }
                            }
                        }

                        "назад" -> {
                            println("Выход в меню")
                            break
                        }

                        else -> {
                            println("Неверная команда. Попробуйте еще раз")
                        }
                    }
                }
            }

            "добавить" -> {
                while (true) {
                    println("Введите данные для нового животного (волк, тигр, медведь, пингвин, кенгуру):")
                    println("Тип:")
                    val animalType = readlnOrNull()?.lowercase(Locale.getDefault())
                    if (animalType !in listOf("волк", "тигр", "медведь", "пингвин", "кенгуру")) {
                        println("Неверный тип животного. Введите один из допустимых")
                        continue
                    }
                    println("Имя:")
                    val name = readlnOrNull()
                    println("Количество ног:")
                    val legCount = readlnOrNull()?.toIntOrNull() ?: 0
                    println("Хищник (true/false):")
                    val isPredator = readlnOrNull()?.toBoolean() ?: false
                    //if (!listOf("true", "false").contains(element = isPredator)) {
                    //    println("Неверный ... животного. Введите один из допустимых")
                    //    continue
                    //}
                    println("Цвет (черный, серый, желтый, белый):")
                    val color = readlnOrNull()?.lowercase(Locale.getDefault())
                    if (color !in listOf("черный", "серый", "желтый", "белый")) {
                        println("Неверный цвет животного. Введите один из допустимых")
                        continue
                    }
                    println("Ареал обитания (Евразия, Северная Америка, Южная Америка, Африка, Австралия, Антарктида):")
                    val habitat = readlnOrNull()
                    if (habitat !in listOf(
                            "евразия",
                            "северная Америка",
                            "южная Америка",
                            "африка",
                            "австралия",
                            "антарктида"
                        )
                    ) {
                        println("Неверный ареал обитания животного. Введите один из допустимых")
                        continue
                    }
                    val newAnimal = Animal(animalType, name, legCount, isPredator, color, habitat)
                    animals.addAnimal(newAnimal)
                    saveAnimalsToFile(filename, animals)
                    println("Животное успешно добавлено")
                    println("Хотите добавить еще одно животное? (да/нет): ")
                    val answer = readlnOrNull()?.trim()?.lowercase(Locale.getDefault())
                    if (answer != "да") {
                        break
                    }
                }
            }

            "удалить" -> {
                // Здесь можно запросить имя животного для удаления и удалить его из списка
                println("Введите имя животного для удаления:")
                val nameToRemove = readlnOrNull()?.trim()
                if (nameToRemove != null) {
                    animals.removeByName(nameToRemove)
                    saveAnimalsToFile(filename, animals)
                    println("Животное успешно удалено\n")
                }
            }

            "выход" -> {
                println("Выход из программы")
                return
            }

            else -> {
                println("Неверная команда. Попробуйте еще раз")
            }
        }
    }
}

