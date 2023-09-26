import java.io.File
import java.io.FileWriter
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader

fun main() {

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
    val animals = loadAnimalsFromFile(filename)

    fun printAllAnimals(animals: MutableList<Animal>){
        for (animal in animals){
            println(animal)
        }
    }

    fun changeName(name: String){
        for (animal in animals) {
            if (animal.name == name) {
                println("Введите новое имя")
                val input = readlnOrNull()
                if (input != null)
                animal.name = input
                println("Имя успешно изменено\n")
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
                val input = readlnOrNull()
                if (input != null) {
                    changeName(input)
                    saveAnimalsToFile(filename, animals)
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
                println("Введите данные для нового животного:")
                println("Имя:")
                val name = readLine()
                println("Тип:")
                val animalType = readLine()
                println("Количество ног:")
                val legCount = readLine()?.toIntOrNull() ?: 0
                println("Хищник (true/false):")
                val isPredator = readLine()?.toBoolean() ?: false
                println("Цвет:")
                val color = readLine()
                println("Ареал обитания:")
                val habitat = readLine()

                val newAnimal = Animal(animalType, name, legCount, isPredator, color, habitat)
                animals.addAnimal(newAnimal)
                println("Новое животное успешно добавлено\n")
                saveAnimalsToFile(filename, animals)
            }

            "удалить" -> {
                // Здесь можно запросить имя животного для удаления и удалить его из списка
                println("Введите имя животного для удаления:")
                val nameToRemove = readlnOrNull()
                if (nameToRemove != null) {
                    animals.removeByName(nameToRemove.trim())
                    println("Животное успешно удалено\n")
                    saveAnimalsToFile(filename, animals)
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

