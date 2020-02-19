package com.capgemini.poc.ebcdic2ascii.writer;

import org.springframework.batch.item.database.JpaItemWriter;

import javax.persistence.EntityManager;
import java.util.List;

public class JpaItemDeleter<T> extends JpaItemWriter<T> {

    @Override
    protected void doWrite(EntityManager entityManager, List<? extends T> items) {

        if (logger.isDebugEnabled()) {
            logger.debug("Deleting to JPA with " + items.size() + " items.");
        }

        if (!items.isEmpty()) {
            long addedToContextCount = 0;
            for (T item : items) {
                entityManager.remove(entityManager.contains(item) ? item : entityManager.merge(item));
                addedToContextCount++;

            }
            if (logger.isDebugEnabled()) {
                logger.debug((items.size() - addedToContextCount) + " entities deleted in persistence context.");
            }
        }

    }

}
