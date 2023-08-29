package ru.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.repository.ActionRepository;
import ru.example.service.ActionService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ActionServiceImpl implements ActionService {

    private final ActionRepository actionRepository;

    @Transactional
    @Override
    public void createNewAction() {

    }
}