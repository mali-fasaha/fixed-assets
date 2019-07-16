import { Route } from '@angular/router';

import { GhaTrackerComponent } from './tracker.component';

export const trackerRoute: Route = {
  path: 'gha-tracker',
  component: GhaTrackerComponent,
  data: {
    pageTitle: 'Real-time user activities'
  }
};
