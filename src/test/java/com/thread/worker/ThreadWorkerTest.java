package com.thread.worker;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Created by David on 2018/1/26.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({ThreadWorker.class, AClass.class})
public class ThreadWorkerTest {

  @Test public void test_run() throws Exception {
    PowerMockito.mockStatic(AClass.class);
    PowerMockito.doNothing().when(AClass.class);
    AClass.register();

    BClass bMock = PowerMockito.mock(BClass.class);
    PowerMockito.doNothing().when(bMock).active();
    PowerMockito.whenNew(BClass.class).withNoArguments().thenReturn(bMock);

    new ThreadWorker().run();

    PowerMockito.verifyStatic(AClass.class, VerificationModeFactory.atLeast(1));
    AClass.register();

    Mockito.verify(bMock, Mockito.times(1)).active();
  }
}