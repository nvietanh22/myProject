=#!/bin/bash

PACKAGE_SUB=$1
MODULE_NAME=$2

if [ -z "$PACKAGE_SUB" ] || [ -z "$MODULE_NAME" ]; then
    echo "Usage: $0 <package_sub_name> <ModuleName>"
    exit 1
fi

# Interactive field input
echo ""
echo "Enter fields for $MODULE_NAME (format: <Type> <name>). Type 'done' to finish:"
FIELDS=()
while true; do
    read -p "> " input
    [[ "$input" == "done" ]] && break
    FIELDS+=("$input")
done
# Ask to generate SQL
read -p "Do you want to generate a SQL create table script? (yes/no): " GEN_SQL
if [[ "$GEN_SQL" == "yes" ]]; then
    read -p "Choose database type (mysql/oracle): " DB_TYPE
    DB_TYPE=$(echo "$DB_TYPE" | tr '[:upper:]' '[:lower:]')

    SCRIPT_DIR="script/db"
    mkdir -p "$SCRIPT_DIR"
    SQL_FILE="$SCRIPT_DIR/${MODULE_NAME,,}_${DB_TYPE}.sql"

    TABLE_NAME=$(echo "$MODULE_NAME" | tr '[:upper:]' '[:lower:]')

    echo "Generating SQL script: $SQL_FILE"

    echo "CREATE TABLE $TABLE_NAME (" > "$SQL_FILE"
    if [[ "$DB_TYPE" == "mysql" ]]; then
        echo "    id BIGINT PRIMARY KEY AUTO_INCREMENT," >> "$SQL_FILE"
    else
        echo "    id NUMBER PRIMARY KEY," >> "$SQL_FILE"
    fi

    for field in "${FIELDS[@]}"; do
        TYPE=$(echo $field | awk '{print $1}')
        NAME=$(echo $field | awk '{print $2}')

        if [[ "$DB_TYPE" == "mysql" ]]; then
            case $TYPE in
                Long|long) SQL_TYPE="BIGINT" ;;
                String|string) SQL_TYPE="VARCHAR(255)" ;;
                Double|double) SQL_TYPE="DOUBLE" ;;
                Integer|int) SQL_TYPE="INT" ;;
                Boolean|boolean) SQL_TYPE="BOOLEAN" ;;
                Date|LocalDate|LocalDateTime) SQL_TYPE="DATETIME" ;;
                *) SQL_TYPE="TEXT" ;;
            esac
        elif [[ "$DB_TYPE" == "oracle" ]]; then
            case $TYPE in
                Long|long) SQL_TYPE="NUMBER(19)" ;;
                String|string) SQL_TYPE="VARCHAR2(255)" ;;
                Double|double) SQL_TYPE="FLOAT" ;;
                Integer|int) SQL_TYPE="NUMBER(10)" ;;
                Boolean|boolean) SQL_TYPE="CHAR(1)" ;;
                Date|LocalDate|LocalDateTime) SQL_TYPE="DATE" ;;
                *) SQL_TYPE="CLOB" ;;
            esac
        else
            echo "❌ Unsupported DB type: $DB_TYPE"
            exit 1
        fi

        echo "    $NAME $SQL_TYPE," >> "$SQL_FILE"
    done

    if [[ "$OSTYPE" == "darwin"* ]]; then
        sed -i '' '$ s/,$//' "$SQL_FILE"
    else
        sed -i '$ s/,$//' "$SQL_FILE"
    fi
    echo ");" >> "$SQL_FILE"
    echo "✅ SQL script generated at: $SQL_FILE"
fi

# Paths and package
PACKAGE_BASE="vn.lottefinance.$PACKAGE_SUB"
SRC_DIR="src/main/java/$(echo "$PACKAGE_BASE" | sed 's/\./\//g')"
ENTITY_DIR="$SRC_DIR/domain"
REPO_DIR="$SRC_DIR/repository"
SERVICE_DIR="$SRC_DIR/service/core"
SERVICE_IMPL_DIR="$SERVICE_DIR/impl"
CONTROLLER_DIR="$SRC_DIR/web/rest"
DTO_DIR="$SERVICE_DIR/dto"

LOWER_MODULE_NAME="$(echo ${MODULE_NAME:0:1} | tr 'A-Z' 'a-z')${MODULE_NAME:1}"

mkdir -p "$ENTITY_DIR" "$REPO_DIR" "$SERVICE_DIR" "$SERVICE_IMPL_DIR" "$CONTROLLER_DIR" "$DTO_DIR"

# ========= Entity =========
ENTITY_FILE="$ENTITY_DIR/${MODULE_NAME}.java"
cat <<EOF > "$ENTITY_FILE"
package $PACKAGE_BASE.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class $MODULE_NAME {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
$(for field in "${FIELDS[@]}"; do
    TYPE=$(echo $field | awk '{print $1}')
    NAME=$(echo $field | awk '{print $2}')
    echo "    private $TYPE $NAME;"
done)
}
EOF

# ========= DTO =========
DTO_FILE="$DTO_DIR/${MODULE_NAME}DTO.java"
cat <<EOF > "$DTO_FILE"
package $PACKAGE_BASE.service.core.dto;

import lombok.*;

public class ${MODULE_NAME}DTO {

    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
$(for field in "${FIELDS[@]}"; do
    TYPE=$(echo $field | awk '{print $1}')
    NAME=$(echo $field | awk '{print $2}')
    echo "        private $TYPE $NAME;"
done)
    }

    @Data
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;
$(for field in "${FIELDS[@]}"; do
    TYPE=$(echo $field | awk '{print $1}')
    NAME=$(echo $field | awk '{print $2}')
    echo "        private $TYPE $NAME;"
done)
    }
}
EOF

# ========= Repository =========
REPO_FILE="$REPO_DIR/${MODULE_NAME}Repository.java"
cat <<EOF > "$REPO_FILE"
package $PACKAGE_BASE.repository;

import $PACKAGE_BASE.domain.$MODULE_NAME;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ${MODULE_NAME}Repository extends JpaRepository<$MODULE_NAME, Long> {
}
EOF

# ========= Service Interface =========
SERVICE_INTERFACE="$SERVICE_DIR/${MODULE_NAME}Service.java"
cat <<EOF > "$SERVICE_INTERFACE"
package $PACKAGE_BASE.service.core;

import $PACKAGE_BASE.service.core.dto.${MODULE_NAME}DTO;

import java.util.List;

public interface ${MODULE_NAME}Service {
    List<${MODULE_NAME}DTO.Response> findAll();
    ${MODULE_NAME}DTO.Response findById(Long id);
    ${MODULE_NAME}DTO.Response create(${MODULE_NAME}DTO.Request request);
    ${MODULE_NAME}DTO.Response update(Long id, ${MODULE_NAME}DTO.Request request);
    void delete(Long id);
}
EOF

# ========= Service Impl =========
SERVICE_IMPL="$SERVICE_IMPL_DIR/${MODULE_NAME}ServiceImpl.java"
cat <<EOF > "$SERVICE_IMPL"
package $PACKAGE_BASE.service.core.impl;

import $PACKAGE_BASE.domain.$MODULE_NAME;
import $PACKAGE_BASE.service.core.dto.${MODULE_NAME}DTO;
import $PACKAGE_BASE.repository.${MODULE_NAME}Repository;
import $PACKAGE_BASE.service.core.${MODULE_NAME}Service;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ${MODULE_NAME}ServiceImpl implements ${MODULE_NAME}Service {

    private final ${MODULE_NAME}Repository repository;

    public ${MODULE_NAME}ServiceImpl(${MODULE_NAME}Repository repository) {
        this.repository = repository;
    }

    @Override
    public List<${MODULE_NAME}DTO.Response> findAll() {
        return repository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public ${MODULE_NAME}DTO.Response findById(Long id) {
        return repository.findById(id).map(this::toResponse).orElse(null);
    }

    @Override
    public ${MODULE_NAME}DTO.Response create(${MODULE_NAME}DTO.Request request) {
        $MODULE_NAME entity = toEntity(request);
        return toResponse(repository.save(entity));
    }

    @Override
    public ${MODULE_NAME}DTO.Response update(Long id, ${MODULE_NAME}DTO.Request request) {
        $MODULE_NAME entity = toEntity(request);
        entity.setId(id);
        return toResponse(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    private ${MODULE_NAME}DTO.Response toResponse($MODULE_NAME entity) {
        return ${MODULE_NAME}DTO.Response.builder()
            .id(entity.getId())
$(for field in "${FIELDS[@]}"; do
    NAME=$(echo $field | awk '{print $2}')
    echo "            .$NAME(entity.get${NAME^}())"
done)
            .build();
    }

    private $MODULE_NAME toEntity(${MODULE_NAME}DTO.Request request) {
        return $MODULE_NAME.builder()
$(for field in "${FIELDS[@]}"; do
    NAME=$(echo $field | awk '{print $2}')
    echo "            .$NAME(request.get${NAME^}())"
done)
            .build();
    }
}
EOF

# ========= Controller =========
CONTROLLER_FILE="$CONTROLLER_DIR/${MODULE_NAME}Controller.java"
cat <<EOF > "$CONTROLLER_FILE"
package $PACKAGE_BASE.web.rest;

import $PACKAGE_BASE.service.core.dto.${MODULE_NAME}DTO;
import $PACKAGE_BASE.service.core.${MODULE_NAME}Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/$LOWER_MODULE_NAME")
public class ${MODULE_NAME}Controller {

    private final ${MODULE_NAME}Service service;

    public ${MODULE_NAME}Controller(${MODULE_NAME}Service service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<${MODULE_NAME}DTO.Response>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<${MODULE_NAME}DTO.Response> getById(@PathVariable Long id) {
        ${MODULE_NAME}DTO.Response dto = service.findById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<${MODULE_NAME}DTO.Response> create(@RequestBody ${MODULE_NAME}DTO.Request request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<${MODULE_NAME}DTO.Response> update(@PathVariable Long id, @RequestBody ${MODULE_NAME}DTO.Request request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
EOF

echo "✅ Done: Generated module '$MODULE_NAME' in package '$PACKAGE_BASE'"
