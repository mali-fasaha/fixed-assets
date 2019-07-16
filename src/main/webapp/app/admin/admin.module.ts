import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { FixedAssetsSharedModule } from 'app/shared';
/* jhipster-needle-add-admin-module-import - JHipster will add admin modules imports here */

import {
  adminState,
  AuditsComponent,
  UserMgmtComponent,
  UserMgmtDetailComponent,
  UserMgmtUpdateComponent,
  UserMgmtDeleteDialogComponent,
  LogsComponent,
  GhaMetricsMonitoringComponent,
  GhaHealthModalComponent,
  GhaHealthCheckComponent,
  GhaConfigurationComponent,
  GhaDocsComponent,
  GhaTrackerComponent
} from './';

@NgModule({
  imports: [
    FixedAssetsSharedModule,
    /* jhipster-needle-add-admin-module - JHipster will add admin modules here */
    RouterModule.forChild(adminState)
  ],
  declarations: [
    AuditsComponent,
    UserMgmtComponent,
    UserMgmtDetailComponent,
    UserMgmtUpdateComponent,
    UserMgmtDeleteDialogComponent,
    LogsComponent,
    GhaConfigurationComponent,
    GhaHealthCheckComponent,
    GhaHealthModalComponent,
    GhaDocsComponent,
    GhaTrackerComponent,
    GhaMetricsMonitoringComponent
  ],
  entryComponents: [UserMgmtDeleteDialogComponent, GhaHealthModalComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FixedAssetsAdminModule {}
