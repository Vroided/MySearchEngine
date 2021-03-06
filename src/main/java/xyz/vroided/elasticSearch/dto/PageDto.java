package xyz.vroided.elasticSearch.dto;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageDto<T> {
    // 是否展示前一页
    private boolean showPre;
    // 是否展示后一页
    private boolean showNext;
    // 是否展示第一页
    private boolean showFirst;
    // 是否展示最后一页
    private boolean showLast;
    // 当前页码
    private int page;
    // 分页大小
    private int size;
    // 当前展示的页码集合
    private List<Integer> pages = new ArrayList<>();
    // 所有页数
    private int totalPage;

    public PageDto(int totalCount, int page, int size) {
        this.page = page;
        this.size = size;
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        // page<1就显示1，page>最大页数就显示最大页数
        if (page < 1) {
            this.page = 1;
        }
        if (page > totalPage) {
            this.page = totalPage;
        }
        // 将需要展示的页码插入到pages中
        pages.add(page);
        for (int i = 1; i <= 3; i++) {
            if (page - i > 0) {
                pages.add(0, page - i);
            }
            if (page + i <= totalPage) {
                pages.add(page + i);
            }
        }
        // 是否展示上一页
        showPre = page != 1;
        // 是否展示后一页
        showNext = page != totalPage;
        // 是否展示第一页
        showFirst = !pages.contains(1);
        // 是否展示最后一页
        showLast = !pages.contains(totalPage);
    }

}
