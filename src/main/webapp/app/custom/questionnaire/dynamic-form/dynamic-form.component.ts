import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { QuestionBase } from 'app/custom/model/question-base.model';
import { FormGroup } from '@angular/forms';
import { QuestionControlService } from 'app/custom/questionnaire/question-control.service';
import { FormFieldControlService } from 'app/custom/questionnaire/dynamic-form/form-field-control.service';
import { NGXLogger } from 'ngx-logger';
import { FormlyFieldConfig } from '@ngx-formly/core';

@Component({
  selector: 'gha-dynamic-form',
  templateUrl: './dynamic-form.component.html',
  styleUrls: ['./dynamic-form.component.scss']
})
export class DynamicFormComponent implements OnInit {
  @Input() questions: QuestionBase<any>[];
  @Output() model = new EventEmitter();
  fields: FormlyFieldConfig[] = [{}];
  queryForm: FormGroup;
  isSubmitting: boolean;

  constructor(private qcs: QuestionControlService, private ffcs: FormFieldControlService, private log: NGXLogger) {}

  ngOnInit() {
    this.fields = this.ffcs.toFormFieldConfig(this.questions);
    this.queryForm = this.qcs.toFormGroup(this.questions);
  }

  previousState() {
    window.history.back();
  }

  submit(model: any) {
    this.isSubmitting = true;
    this.model.emit(this.queryForm);
  }

  protected onSubmitSuccess() {
    this.isSubmitting = false;
    this.previousState();
  }

  protected onSubmitError() {
    this.isSubmitting = false;
  }
}
