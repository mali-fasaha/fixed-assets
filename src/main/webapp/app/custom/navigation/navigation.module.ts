import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { NavigationRoutingModule } from './navigation-routing.module';
import { AboutMenuComponent } from './about-menu/about-menu.component';
import { ModelMenuComponent } from './model-menu/model-menu.component';
import { ReportMenuComponent } from './report-menu/report-menu.component';
import { ImportMenuComponent } from './import-menu/import-menu.component';
import { FixedAssetsSharedModule } from 'app/shared';
import { MaintenanceMenuComponent } from './maintenance-menu/maintenance-menu.component';
import { DataTablesMenuComponent } from './data-tables-menu/data-tables-menu.component';

@NgModule({
  declarations: [
    AboutMenuComponent,
    ModelMenuComponent,
    ReportMenuComponent,
    ImportMenuComponent,
    MaintenanceMenuComponent,
    DataTablesMenuComponent
  ],
  imports: [CommonModule, NavigationRoutingModule, FixedAssetsSharedModule],
  exports: [
    AboutMenuComponent,
    ModelMenuComponent,
    ReportMenuComponent,
    ImportMenuComponent,
    MaintenanceMenuComponent,
    DataTablesMenuComponent
  ],
  entryComponents: [
    AboutMenuComponent,
    ModelMenuComponent,
    ReportMenuComponent,
    ImportMenuComponent,
    MaintenanceMenuComponent,
    DataTablesMenuComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NavigationModule {}
