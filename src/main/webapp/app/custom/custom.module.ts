import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CustomRoutingModule } from './custom-routing.module';
import { AboutFixedAssetsModule } from 'app/custom/about-fixed-assets';
import { AboutFixedAssetsRoutingModule } from 'app/custom/about-fixed-assets/about-fixed-assets-routing.module';
import { NavigationModule } from './navigation/navigation.module';
import { QuestionnaireModule } from './questionnaire/questionnaire.module';
import { AppDataTablesModule } from './app-data-tables/app-data-tables.module';
import { ReportingModule } from './reporting/reporting.module';

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    CustomRoutingModule,
    AboutFixedAssetsModule,
    NavigationModule,
    QuestionnaireModule,
    AppDataTablesModule,
    ReportingModule
  ],
  exports: [AboutFixedAssetsModule, AboutFixedAssetsRoutingModule, NavigationModule]
})
export class CustomModule {}
