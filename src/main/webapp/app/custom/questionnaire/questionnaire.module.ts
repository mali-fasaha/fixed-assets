import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { QuestionnaireRoutingModule } from './questionnaire-routing.module';
import { GhaMaterialModule } from 'app/custom/gha-material.module';
import { ReactiveFormsModule } from '@angular/forms';
import { FormlyModule } from '@ngx-formly/core';
import { FormlyMaterialModule } from '@ngx-formly/material';
import { DynamicFormComponent } from './dynamic-form/dynamic-form.component';

@NgModule({
  declarations: [DynamicFormComponent],
  imports: [
    CommonModule,
    QuestionnaireRoutingModule,
    GhaMaterialModule,
    ReactiveFormsModule,
    FormlyModule.forRoot({
      validationMessages: [{ name: 'required', message: 'This field is required' }]
    }),
    FormlyMaterialModule
  ],
  exports: [QuestionnaireRoutingModule, DynamicFormComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  entryComponents: [DynamicFormComponent]
})
export class QuestionnaireModule {}
