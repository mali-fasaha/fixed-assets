import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import { CommonModule } from '@angular/common';

import { QuestionnaireRoutingModule } from './questionnaire-routing.module';

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    QuestionnaireRoutingModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class QuestionnaireModule { }
