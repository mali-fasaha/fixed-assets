import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class MessageTokenComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('gha-message-token div table .btn-danger'));
  title = element.all(by.css('gha-message-token div h2#page-heading span')).first();

  async clickOnCreateButton(timeout?: number) {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(timeout?: number) {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons() {
    return this.deleteButtons.count();
  }

  async getTitle() {
    return this.title.getText();
  }
}

export class MessageTokenUpdatePage {
  pageTitle = element(by.id('gha-message-token-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  descriptionInput = element(by.id('field_description'));
  timeSentInput = element(by.id('field_timeSent'));
  tokenValueInput = element(by.id('field_tokenValue'));
  receivedInput = element(by.id('field_received'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setDescriptionInput(description) {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput() {
    return await this.descriptionInput.getAttribute('value');
  }

  async setTimeSentInput(timeSent) {
    await this.timeSentInput.sendKeys(timeSent);
  }

  async getTimeSentInput() {
    return await this.timeSentInput.getAttribute('value');
  }

  async setTokenValueInput(tokenValue) {
    await this.tokenValueInput.sendKeys(tokenValue);
  }

  async getTokenValueInput() {
    return await this.tokenValueInput.getAttribute('value');
  }

  getReceivedInput(timeout?: number) {
    return this.receivedInput;
  }
  async save(timeout?: number) {
    await this.saveButton.click();
  }

  async cancel(timeout?: number) {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class MessageTokenDeleteDialog {
  private dialogTitle = element(by.id('gha-delete-messageToken-heading'));
  private confirmButton = element(by.id('gha-confirm-delete-messageToken'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
