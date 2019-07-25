import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class TransactionApprovalComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('gha-transaction-approval div table .btn-danger'));
  title = element.all(by.css('gha-transaction-approval div h2#page-heading span')).first();

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

export class TransactionApprovalUpdatePage {
  pageTitle = element(by.id('gha-transaction-approval-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  descriptionInput = element(by.id('field_description'));
  requestedByInput = element(by.id('field_requestedBy'));
  recommendedByInput = element(by.id('field_recommendedBy'));
  reviewedByInput = element(by.id('field_reviewedBy'));
  firstApproverInput = element(by.id('field_firstApprover'));
  secondApproverInput = element(by.id('field_secondApprover'));
  thirdApproverInput = element(by.id('field_thirdApprover'));
  fourthApproverInput = element(by.id('field_fourthApprover'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setDescriptionInput(description) {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput() {
    return await this.descriptionInput.getAttribute('value');
  }

  async setRequestedByInput(requestedBy) {
    await this.requestedByInput.sendKeys(requestedBy);
  }

  async getRequestedByInput() {
    return await this.requestedByInput.getAttribute('value');
  }

  async setRecommendedByInput(recommendedBy) {
    await this.recommendedByInput.sendKeys(recommendedBy);
  }

  async getRecommendedByInput() {
    return await this.recommendedByInput.getAttribute('value');
  }

  async setReviewedByInput(reviewedBy) {
    await this.reviewedByInput.sendKeys(reviewedBy);
  }

  async getReviewedByInput() {
    return await this.reviewedByInput.getAttribute('value');
  }

  async setFirstApproverInput(firstApprover) {
    await this.firstApproverInput.sendKeys(firstApprover);
  }

  async getFirstApproverInput() {
    return await this.firstApproverInput.getAttribute('value');
  }

  async setSecondApproverInput(secondApprover) {
    await this.secondApproverInput.sendKeys(secondApprover);
  }

  async getSecondApproverInput() {
    return await this.secondApproverInput.getAttribute('value');
  }

  async setThirdApproverInput(thirdApprover) {
    await this.thirdApproverInput.sendKeys(thirdApprover);
  }

  async getThirdApproverInput() {
    return await this.thirdApproverInput.getAttribute('value');
  }

  async setFourthApproverInput(fourthApprover) {
    await this.fourthApproverInput.sendKeys(fourthApprover);
  }

  async getFourthApproverInput() {
    return await this.fourthApproverInput.getAttribute('value');
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

export class TransactionApprovalDeleteDialog {
  private dialogTitle = element(by.id('gha-delete-transactionApproval-heading'));
  private confirmButton = element(by.id('gha-confirm-delete-transactionApproval'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
