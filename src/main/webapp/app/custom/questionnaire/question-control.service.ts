import { Injectable } from '@angular/core';
import { QuestionBase } from 'app/custom/model/question-base.model';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class QuestionControlService {
  /**
   * Transforms question metadata into a formGroup object
   *
   * @param questions Question metadata
   */
  toFormGroup(questions: QuestionBase<any>[]): FormGroup {
    const group: any = {};
    questions.forEach(question => {
      const validatedFormControl: FormControl = new FormControl(question.value || '', Validators.required);
      const nonValidatedFormControl: FormControl = new FormControl(question.value || '');
      group[question.key] = question.required ? validatedFormControl : nonValidatedFormControl;
    });

    return new FormGroup(group);
  }
}
