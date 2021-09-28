package top.ordinaryroad.commons.core.base.request.query;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Created by Ergin
 **/
public class OffsetBasedPageRequest implements Pageable {

    private final int limit;
    private final int offset;
    private final Sort sort;

    /**
     * Creates a new {@link OffsetBasedPageRequest} with sort parameters applied.
     *
     * @param offset zero-based offset.
     * @param limit  the size of the elements to be returned.
     * @param sort   can be {@literal null}.
     */
    public OffsetBasedPageRequest(int offset, int limit, Sort sort) {
        if (offset < 0) {
            throw new IllegalArgumentException("Offset index must not be less than zero!");
        }

        if (limit < 1) {
            throw new IllegalArgumentException("Limit must not be less than one!");
        }
        this.limit = limit;
        this.offset = offset;
        this.sort = sort;
    }

    /**
     * Creates a new {@link OffsetBasedPageRequest} with sort parameters applied.
     *
     * @param offset     zero-based offset.
     * @param limit      the size of the elements to be returned.
     * @param direction  the direction of the {@link Sort} to be specified, can be {@literal null}.
     * @param properties the properties to sort by, must not be {@literal null} or empty.
     */
    public OffsetBasedPageRequest(int offset, int limit, Sort.Direction direction, String... properties) {
        this(offset, limit, Sort.by(direction, properties));
    }

    /**
     * Creates a new {@link OffsetBasedPageRequest} with sort parameters applied.
     *
     * @param offset zero-based offset.
     * @param limit  the size of the elements to be returned.
     */
    public OffsetBasedPageRequest(int offset, int limit) {
        this(offset, limit, Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public int getPageNumber() {
        return offset / limit;
    }

    @Override
    public int getPageSize() {
        return limit;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @NotNull
    @Override
    public Sort getSort() {
        return sort;
    }

    @NotNull
    @Override
    public Pageable next() {
        return new OffsetBasedPageRequest(Math.toIntExact(getOffset() + getPageSize()), getPageSize(), getSort());
    }

    public OffsetBasedPageRequest previous() {
        return hasPrevious() ? new OffsetBasedPageRequest(Math.toIntExact(getOffset() - getPageSize()), getPageSize(), getSort()) : this;
    }

    @NotNull
    @Override
    public Pageable previousOrFirst() {
        return hasPrevious() ? previous() : first();
    }

    @NotNull
    @Override
    public Pageable first() {
        return new OffsetBasedPageRequest(0, getPageSize(), getSort());
    }

    @NotNull
    @Override
    public Pageable withPage(int pageNumber) {
        return new OffsetBasedPageRequest(pageNumber * 10, 10);
    }

    @Override
    public boolean hasPrevious() {
        return offset > limit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OffsetBasedPageRequest)) {
            return false;
        }
        OffsetBasedPageRequest that = (OffsetBasedPageRequest) o;
        return limit == that.limit &&
                getOffset() == that.getOffset() &&
                Objects.equals(getSort(), that.getSort());
    }

    @Override
    public int hashCode() {
        return Objects.hash(limit, getOffset(), getSort());
    }

    /**
     * Creates a new {@link OffsetBasedPageRequest} with sort parameters applied.
     *
     * @param offset 从第几个开始
     * @param limit  多少条数据
     * @param sort   must not be {@literal null}, use {@link Sort#unsorted()} instead.
     * @since 2.0
     */
    public static OffsetBasedPageRequest of(int offset, int limit, Sort sort) {
        return new OffsetBasedPageRequest(offset, limit, sort);
    }

    /**
     * Creates a new {@link OffsetBasedPageRequest} with sort parameters applied.
     * 默认Id增序
     *
     * @param offset 从第几个开始
     * @param limit  多少条数据
     * @since 2.0
     */
    public static OffsetBasedPageRequest of(int offset, int limit) {
        return new OffsetBasedPageRequest(offset, limit);
    }

}