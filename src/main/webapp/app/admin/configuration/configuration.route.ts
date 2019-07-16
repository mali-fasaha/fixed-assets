import { Route } from '@angular/router';

import { GhaConfigurationComponent } from './configuration.component';

export const configurationRoute: Route = {
  path: 'gha-configuration',
  component: GhaConfigurationComponent,
  data: {
    pageTitle: 'Configuration'
  }
};
