package com.yusheng.contactbackend1.pojo;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.yusheng.contactbackend1.mapper.PeopleMapper;

import java.awt.event.ActionListener;
import java.util.List;

public class PeopleListener implements ReadListener<People> {

    private static final int BATCH_COUNT = 100;

    private List<People> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    private PeopleMapper peopleMapper;


    public PeopleListener(PeopleMapper peopleMapper) {
        this.peopleMapper = peopleMapper;
    }
    @Override
    public void invoke(People people, AnalysisContext analysisContext) {
        cachedDataList.add(people);

        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();

            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        saveData();
    }

    private void saveData() {
        peopleMapper.save(cachedDataList);
    }
}
