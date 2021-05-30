package org.girevoy.tablemanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class TableManagerApplication2 implements CommandLineRunner {
    private final ApplicationContext context;

    @Autowired
    public TableManagerApplication2(ApplicationContext context) {
        this.context = context;
    }

    public static void main(String[] args) {
        SpringApplication.run(TableManagerApplication2.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        EntityRow entityRow = new EntityRow();
//        entityRow.setTableName("test");
//        Map<String, Object> attr = new LinkedHashMap<>();
//        attr.put("param", "string");
//        attr.put("date", LocalDate.now());
//        attr.put("number", 10);
//        entityRow.setAttributes(attr);
//
//        EntityRowDao dao = context.getBean(EntityRowDao.class);
//        dao.insert(entityRow);
//
//        Map<String, Object> attr2 = new LinkedHashMap<>();
//        attr.put("param", "string222");
//        attr.put("date", LocalDate.now());
//        attr.put("number", 10);
//        entityRow.setAttributes(attr);
//
//        int updated = dao.update(entityRow);

//        List<EntityRow> list = dao.findAll("test");
//        EntityRow row = dao.findById("test", 6);
    }
}
