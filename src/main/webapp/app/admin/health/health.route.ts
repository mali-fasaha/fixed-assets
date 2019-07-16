import { Route } from '@angular/router';

import { GhaHealthCheckComponent } from './health.component';

export const healthRoute: Route = {
  path: 'gha-health',
  component: GhaHealthCheckComponent,
  data: {
    pageTitle: 'Health Checks'
  }
};
