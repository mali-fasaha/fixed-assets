import { Injectable } from '@angular/core';
import { QuestionBase } from 'app/custom/model/question-base.model';
import { FormlyFieldConfig } from '@ngx-formly/core';

/**
 * This service transforms a QuestionBase object which is used to abstract
 * field-building into FormlyFieldConfig array
 *
 */
@Injectable({
  providedIn: 'root'
})
export class FormFieldControlService {
  toFormFieldConfig(questions: QuestionBase<any>[]): FormlyFieldConfig[] {
    const fields: FormlyFieldConfig[] = [{}];

    questions.forEach((question: QuestionBase<any>) => {
      fields.push({
        key: question.key,
        type: question.fieldType,
        templateOptions: {
          required: question.required,
          label: question.label,
          options: question.selectOptions
        }
      });
    });
    return fields;
  }
}
