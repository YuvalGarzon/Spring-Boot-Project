package demo.Repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

public interface ObjectCrud extends MongoRepository<ObjectEntity, String> {

    public Optional<ObjectEntity> findByTypeAndUserEmail(@Param("type") String type, @Param("email") String email);

    public Page<ObjectEntity> findAllByType(@Param("type") String type, Pageable pageable);

    public Page<ObjectEntity> findAllByAlias(@Param("alias") String alias, Pageable pageable);

    public Page<ObjectEntity> findAllByStatus(@Param("status") ObjectStatus status, Pageable pageable);

    public Page<ObjectEntity> findAllByTypeAndStatus(@Param("type") String type, @Param("status") ObjectStatus status,
                                                     Pageable pageable);

    public Page<ObjectEntity> findAllByParent_ObjectIdAndSystemID(@Param("parentId") String parentId,
                                                                  @Param("parentSystemId") String parentSystemId, Pageable pageable);

    public Page<ObjectEntity> findAllByAliasLike(@Param("pattern") String pattern, Pageable pageable);

    // Active-only variants
    public Page<ObjectEntity> findAllByActiveTrue(Pageable pageable);

    public Page<ObjectEntity> findAllByTypeAndActiveTrue(@Param("type") String type, Pageable pageable);

    public Page<ObjectEntity> findAllByAliasAndActiveTrue(@Param("alias") String alias, Pageable pageable);

    public Page<ObjectEntity> findAllByStatusAndActiveTrue(@Param("status") ObjectStatus status, Pageable pageable);

    public Page<ObjectEntity> findAllByTypeAndStatusAndActiveTrue(@Param("type") String type,
                                                                  @Param("status") ObjectStatus status, Pageable pageable);

    public Page<ObjectEntity> findAllByParent_ObjectIdAndSystemIDAndActiveTrue(@Param("parentId") String parentId,
                                                                               @Param("parentSystemId") String parentSystemId, Pageable pageable);

    public Page<ObjectEntity> findAllByAliasLikeAndActiveTrue(@Param("pattern") String pattern, Pageable pageable);

}
