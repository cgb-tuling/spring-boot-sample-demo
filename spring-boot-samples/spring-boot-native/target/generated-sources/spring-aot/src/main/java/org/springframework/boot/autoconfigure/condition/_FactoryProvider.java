package org.springframework.boot.autoconfigure.condition;

public abstract class _FactoryProvider {
  public static OnBeanCondition onBeanCondition() {
    return new OnBeanCondition();
  }

  public static OnClassCondition onClassCondition() {
    return new OnClassCondition();
  }

  public static OnWebApplicationCondition onWebApplicationCondition() {
    return new OnWebApplicationCondition();
  }

  public static ConditionEvaluationReportAutoConfigurationImportListener conditionEvaluationReportAutoConfigurationImportListener(
      ) {
    return new ConditionEvaluationReportAutoConfigurationImportListener();
  }
}
