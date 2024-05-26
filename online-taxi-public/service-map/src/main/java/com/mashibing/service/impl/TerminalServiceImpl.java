package com.mashibing.service.impl;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.remote.TerminalClint;
import com.mashibing.service.TerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TerminalServiceImpl implements TerminalService {
    @Autowired
   private TerminalClint terminalClint;

    @Override
    public ResponseResult add(String name) {

        return terminalClint.add(name);
    }
}
