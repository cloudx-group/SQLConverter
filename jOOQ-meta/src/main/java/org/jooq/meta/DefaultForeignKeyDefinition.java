/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Other licenses:
 * -----------------------------------------------------------------------------
 * Commercial licenses for this work are available. These replace the above
 * ASL 2.0 and offer limited warranties, support, maintenance, and commercial
 * database integrations.
 *
 * For more information, please visit: https://www.jooq.org/legal/licensing
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
package org.jooq.meta;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DefaultForeignKeyDefinition extends AbstractConstraintDefinition implements ForeignKeyDefinition {

    private final List<ColumnDefinition>  fkColumns;
    private final List<ColumnDefinition>  ukColumns;
    private final UniqueKeyDefinition     uk;

    public DefaultForeignKeyDefinition(SchemaDefinition schema, String name, TableDefinition table, UniqueKeyDefinition uniqueKey) {
        this(schema, name, table, uniqueKey, true);
    }

    public DefaultForeignKeyDefinition(SchemaDefinition schema, String name, TableDefinition table, UniqueKeyDefinition uk, boolean enforced) {
        super(schema, table, name, enforced);

        this.fkColumns = new ArrayList<>();
        this.ukColumns = new ArrayList<>();
        this.uk = uk;
    }

    @Override
    public TableDefinition getKeyTable() {
        return getTable();
    }

    @Override
    public List<ColumnDefinition> getKeyColumns() {
        return fkColumns;
    }

    @Override
    public UniqueKeyDefinition getReferencedKey() {
        return uk;
    }

    @Override
    public UniqueKeyDefinition resolveReferencedKey() {
        return uk.resolveReferencedKey();
    }

    @Override
    public TableDefinition getReferencedTable() {
        return uk.getTable();
    }

    @Override
    public List<ColumnDefinition> getReferencedColumns() {
        return ukColumns;
    }

    @Override
    public int countSimilarReferences() {
        Set<String> keys = new HashSet<>();

        for (ForeignKeyDefinition key : getDatabase().getRelations().getForeignKeys(getTable()))
            if (key.getReferencedTable().equals(getReferencedTable()))
                keys.add(key.getName());

        return keys.size();
    }
}
