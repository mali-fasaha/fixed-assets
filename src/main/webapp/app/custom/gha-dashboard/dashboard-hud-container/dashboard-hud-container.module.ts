import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DashboardHudDeckRoutingModule } from './dashboard-hud-deck-routing.module';
import { DashboardHudComponent } from 'app/custom/gha-dashboard/dashboard-hud-container/dashboard-hud/dashboard-hud.component';
import { FixedAssetsSharedModule } from 'app/shared';
import { DashboardHudOutstandingPrepsComponent } from './dashboard-hud-outstanding-preps/dashboard-hud-outstanding-preps.component';
import { DashboardHudAmortizationComponent } from './dashboard-hud-amortization/dashboard-hud-amortization.component';
import { DashboardHudOrphanedAmortizationsComponent } from './dashboard-hud-orphaned-amortizations/dashboard-hud-orphaned-amortizations.component';
import { CountUpModule } from 'countup.js-angular2';

@NgModule({
  declarations: [
    DashboardHudComponent,
    DashboardHudOutstandingPrepsComponent,
    DashboardHudAmortizationComponent,
    DashboardHudOrphanedAmortizationsComponent
  ],
  exports: [DashboardHudComponent],
  imports: [CommonModule, FixedAssetsSharedModule, DashboardHudDeckRoutingModule, CountUpModule],
  entryComponents: [
    DashboardHudComponent,
    DashboardHudOutstandingPrepsComponent,
    DashboardHudAmortizationComponent,
    DashboardHudOrphanedAmortizationsComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DashboardHudContainerModule {}
