package es.xagani.grailsTestAutowired

class Item {

    String name
    String description

    static constraints = {
        name nullable: false
        description nullable: true
    }

}
