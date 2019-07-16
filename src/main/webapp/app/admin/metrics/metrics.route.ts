import { Route } from '@angular/router';

import { GhaMetricsMonitoringComponent } from './metrics.component';

export const metricsRoute: Route = {
  path: 'gha-metrics',
  component: GhaMetricsMonitoringComponent,
  data: {
    pageTitle: 'Application Metrics'
  }
};
