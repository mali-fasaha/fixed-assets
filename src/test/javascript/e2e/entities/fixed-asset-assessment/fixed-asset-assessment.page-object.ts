import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class FixedAssetAssessmentComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('gha-fixed-asset-assessment div table .btn-danger'));
  title = element.all(by.css('gha-fixed-asset-assessment div h2#page-heading span')).first();

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

export class FixedAssetAssessmentUpdatePage {
  pageTitle = element(by.id('gha-fixed-asset-assessment-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  descriptionInput = element(by.id('field_description'));
  assetConditionSelect = element(by.id('field_assetCondition'));
  assessmentDateInput = element(by.id('field_assessmentDate'));
  assessmentRemarksInput = element(by.id('field_assessmentRemarks'));
  nameOfAssessingStaffInput = element(by.id('field_nameOfAssessingStaff'));
  nameOfAssessmentContractorInput = element(by.id('field_nameOfAssessmentContractor'));
  currentServiceOutletCodeInput = element(by.id('field_currentServiceOutletCode'));
  currentPhysicalAddressInput = element(by.id('field_currentPhysicalAddress'));
  nextAssessmentDateInput = element(by.id('field_nextAssessmentDate'));
  nameOfUserInput = element(by.id('field_nameOfUser'));
  fixedAssetPictureInput = element(by.id('file_fixedAssetPicture'));
  fixedAssetItemIdInput = element(by.id('field_fixedAssetItemId'));
  estimatedValueInput = element(by.id('field_estimatedValue'));
  estimatedUsefulMonthsInput = element(by.id('field_estimatedUsefulMonths'));

  async getPageTitle() {
    return this.pageTitle.getText();
  }

  async setDescriptionInput(description) {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput() {
    return await this.descriptionInput.getAttribute('value');
  }

  async setAssetConditionSelect(assetCondition) {
    await this.assetConditionSelect.sendKeys(assetCondition);
  }

  async getAssetConditionSelect() {
    return await this.assetConditionSelect.element(by.css('option:checked')).getText();
  }

  async assetConditionSelectLastOption(timeout?: number) {
    await this.assetConditionSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setAssessmentDateInput(assessmentDate) {
    await this.assessmentDateInput.sendKeys(assessmentDate);
  }

  async getAssessmentDateInput() {
    return await this.assessmentDateInput.getAttribute('value');
  }

  async setAssessmentRemarksInput(assessmentRemarks) {
    await this.assessmentRemarksInput.sendKeys(assessmentRemarks);
  }

  async getAssessmentRemarksInput() {
    return await this.assessmentRemarksInput.getAttribute('value');
  }

  async setNameOfAssessingStaffInput(nameOfAssessingStaff) {
    await this.nameOfAssessingStaffInput.sendKeys(nameOfAssessingStaff);
  }

  async getNameOfAssessingStaffInput() {
    return await this.nameOfAssessingStaffInput.getAttribute('value');
  }

  async setNameOfAssessmentContractorInput(nameOfAssessmentContractor) {
    await this.nameOfAssessmentContractorInput.sendKeys(nameOfAssessmentContractor);
  }

  async getNameOfAssessmentContractorInput() {
    return await this.nameOfAssessmentContractorInput.getAttribute('value');
  }

  async setCurrentServiceOutletCodeInput(currentServiceOutletCode) {
    await this.currentServiceOutletCodeInput.sendKeys(currentServiceOutletCode);
  }

  async getCurrentServiceOutletCodeInput() {
    return await this.currentServiceOutletCodeInput.getAttribute('value');
  }

  async setCurrentPhysicalAddressInput(currentPhysicalAddress) {
    await this.currentPhysicalAddressInput.sendKeys(currentPhysicalAddress);
  }

  async getCurrentPhysicalAddressInput() {
    return await this.currentPhysicalAddressInput.getAttribute('value');
  }

  async setNextAssessmentDateInput(nextAssessmentDate) {
    await this.nextAssessmentDateInput.sendKeys(nextAssessmentDate);
  }

  async getNextAssessmentDateInput() {
    return await this.nextAssessmentDateInput.getAttribute('value');
  }

  async setNameOfUserInput(nameOfUser) {
    await this.nameOfUserInput.sendKeys(nameOfUser);
  }

  async getNameOfUserInput() {
    return await this.nameOfUserInput.getAttribute('value');
  }

  async setFixedAssetPictureInput(fixedAssetPicture) {
    await this.fixedAssetPictureInput.sendKeys(fixedAssetPicture);
  }

  async getFixedAssetPictureInput() {
    return await this.fixedAssetPictureInput.getAttribute('value');
  }

  async setFixedAssetItemIdInput(fixedAssetItemId) {
    await this.fixedAssetItemIdInput.sendKeys(fixedAssetItemId);
  }

  async getFixedAssetItemIdInput() {
    return await this.fixedAssetItemIdInput.getAttribute('value');
  }

  async setEstimatedValueInput(estimatedValue) {
    await this.estimatedValueInput.sendKeys(estimatedValue);
  }

  async getEstimatedValueInput() {
    return await this.estimatedValueInput.getAttribute('value');
  }

  async setEstimatedUsefulMonthsInput(estimatedUsefulMonths) {
    await this.estimatedUsefulMonthsInput.sendKeys(estimatedUsefulMonths);
  }

  async getEstimatedUsefulMonthsInput() {
    return await this.estimatedUsefulMonthsInput.getAttribute('value');
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

export class FixedAssetAssessmentDeleteDialog {
  private dialogTitle = element(by.id('gha-delete-fixedAssetAssessment-heading'));
  private confirmButton = element(by.id('gha-confirm-delete-fixedAssetAssessment'));

  async getDialogTitle() {
    return this.dialogTitle.getText();
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
