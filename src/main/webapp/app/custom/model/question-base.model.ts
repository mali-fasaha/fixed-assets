/**
 * Represents metadata for the forms
 */
export class QuestionBase<T> {
  value: T;
  className: string;
  key: string;
  label: string;
  required: boolean;
  order: number;
  fieldType: string; // e.g. 'input', 'file', 'checkbox','richEditor', 'select'
  controlType: string;
  templateOptionsType: string; // e.g. 'grouped',
  templateOptionsLabel: string; // e.g 'A nice grouped select',
  templateOptionsRequired: boolean; // e.g true,false
  templateOptionsDescription: string; // e.g 'a grouped select',
  selectOptions: { name: string; value: string; group: string }[]; // e.g. [{name: 'option 1', value: 0, group: 'group for option'},{name: 'option 2', value: 1, group: 'group for option'}]

  constructor(
    options: {
      value?: T;
      className?: string;
      key?: string;
      label?: string;
      required?: boolean;
      order?: number;
      fieldType?: string;
      controlType?: string;
      templateOptionsType?: string;
      templateOptionsLabel?: string;
      templateOptionsRequired?: boolean;
      templateOptionsDescription?: string;
      selectOptions?: { name: string; value: string; group: string }[];
    } = {}
  ) {
    this.value = options.value;
    this.className = options.className || '';
    this.key = options.key || '';
    this.label = options.label || '';
    this.required = !!options.required;
    this.order = options.order === undefined ? 1 : options.order;
    this.fieldType = options.fieldType;
    this.controlType = options.controlType || '';
    this.templateOptionsType = options.templateOptionsType || '';
    this.templateOptionsLabel = options.templateOptionsLabel || '';
    this.templateOptionsRequired = !!options.templateOptionsRequired;
    this.templateOptionsDescription = options.templateOptionsDescription || '';
    this.selectOptions = options.selectOptions || [];
  }
}

export class TextBoxQuestion extends QuestionBase<string> {
  controlType = 'textbox';
  type: string;

  constructor(options: {} = {}) {
    super(options);
    /* tslint:disable:no-string-literal */
    this.type = options['type'] || '';
    /* tslint:enable:no-string-literal */
  }
}

export class DropdownQuestion extends QuestionBase<string> {
  controlType = 'dropdown';
  options: { name: string; value: string; group: string }[] = [];

  constructor(options: {} = {}) {
    super(options);
    /* tslint:disable:no-string-literal */
    this.options = options['options'] || [];
    /* tslint:enable:no-string-literal */
  }
}
