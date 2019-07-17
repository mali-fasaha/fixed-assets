import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CustomRoutingModule } from './custom-routing.module';
import { AboutFixedAssetsModule } from 'app/custom/about-fixed-assets';
import { AboutFixedAssetsRoutingModule } from 'app/custom/about-fixed-assets/about-fixed-assets-routing.module';
import { NavigationModule } from './navigation/navigation.module';
import { QuestionnaireModule } from './questionnaire/questionnaire.module';
import { DataExportModule } from './data-export/data-export.module';

@NgModule({
  declarations: [],
  imports: [CommonModule, CustomRoutingModule, AboutFixedAssetsModule, NavigationModule, QuestionnaireModule, DataExportModule],
  exports: [AboutFixedAssetsModule, AboutFixedAssetsRoutingModule, NavigationModule, DataExportModule]
})
export class CustomModule {}
