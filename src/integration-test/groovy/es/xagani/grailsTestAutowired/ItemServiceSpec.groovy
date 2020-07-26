package es.xagani.grailsTestAutowired

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class ItemServiceSpec extends Specification {

    ItemService itemService
    SessionFactory sessionFactory

    private Long setupData() {
        new Item(name: 'item1', description: 'Item #1').save(flush: true, failOnError: true)
        new Item(name: 'item2', description: 'Item #2').save(flush: true, failOnError: true)
        Item item = new Item(name: 'item3', description: 'Item #3').save(flush: true, failOnError: true)
        new Item(name: 'item4', description: 'Item #4').save(flush: true, failOnError: true)
        new Item(name: 'item5', description: 'Item #5').save(flush: true, failOnError: true)
        item.id
    }

    void "test get"() {
        setupData()

        expect:
        itemService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Item> itemList = itemService.list(max: 2, offset: 2)

        then:
        itemList.size() == 2
        itemList[0].name == 'item3'
        itemList[1].name == 'item4'
    }

    void "test count"() {
        setupData()

        expect:
        itemService.count() == 5
    }

    void "test delete"() {
        Long itemId = setupData()

        expect:
        itemService.count() == 5

        when:
        itemService.delete(itemId)
        sessionFactory.currentSession.flush()

        then:
        itemService.count() == 4
    }

    void "test save"() {
        when:
        Item item = new Item(
                name: 'newItem',
                description: 'A new Item'
        )
        itemService.save(item)

        then:
        item.id != null
    }
}
