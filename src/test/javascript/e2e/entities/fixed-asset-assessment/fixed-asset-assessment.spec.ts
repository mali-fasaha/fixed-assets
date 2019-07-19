/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  FixedAssetAssessmentComponentsPage,
  FixedAssetAssessmentDeleteDialog,
  FixedAssetAssessmentUpdatePage
} from './fixed-asset-assessment.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('FixedAssetAssessment e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let fixedAssetAssessmentUpdatePage: FixedAssetAssessmentUpdatePage;
  let fixedAssetAssessmentComponentsPage: FixedAssetAssessmentComponentsPage;
  let fixedAssetAssessmentDeleteDialog: FixedAssetAssessmentDeleteDialog;
  const fileNameToUpload = 'logo-jhipster.png';
  const fileToUpload = '../../../../../../src/main/webapp/content/images/' + fileNameToUpload;
  const absolutePath = path.resolve(__dirname, fileToUpload);

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load FixedAssetAssessments', async () => {
    await navBarPage.goToEntity('fixed-asset-assessment');
    fixedAssetAssessmentComponentsPage = new FixedAssetAssessmentComponentsPage();
    await browser.wait(ec.visibilityOf(fixedAssetAssessmentComponentsPage.title), 5000);
    expect(await fixedAssetAssessmentComponentsPage.getTitle()).to.eq('Fixed Asset Assessments');
  });

  it('should load create FixedAssetAssessment page', async () => {
    await fixedAssetAssessmentComponentsPage.clickOnCreateButton();
    fixedAssetAssessmentUpdatePage = new FixedAssetAssessmentUpdatePage();
    expect(await fixedAssetAssessmentUpdatePage.getPageTitle()).to.eq('Create or edit a Fixed Asset Assessment');
    await fixedAssetAssessmentUpdatePage.cancel();
  });

  it('should create and save FixedAssetAssessments', async () => {
    const nbButtonsBeforeCreate = await fixedAssetAssessmentComponentsPage.countDeleteButtons();

    await fixedAssetAssessmentComponentsPage.clickOnCreateButton();
    await promise.all([
      fixedAssetAssessmentUpdatePage.setDescriptionInput('description'),
      fixedAssetAssessmentUpdatePage.assetConditionSelectLastOption(),
      fixedAssetAssessmentUpdatePage.setAssessmentDateInput('2000-12-31'),
      fixedAssetAssessmentUpdatePage.setAssessmentRemarksInput('assessmentRemarks'),
      fixedAssetAssessmentUpdatePage.setNameOfAssessingStaffInput('nameOfAssessingStaff'),
      fixedAssetAssessmentUpdatePage.setNameOfAssessmentContractorInput('nameOfAssessmentContractor'),
      fixedAssetAssessmentUpdatePage.setCurrentServiceOutletCodeInput('currentServiceOutletCode'),
      fixedAssetAssessmentUpdatePage.setCurrentPhysicalAddressInput('currentPhysicalAddress'),
      fixedAssetAssessmentUpdatePage.setNextAssessmentDateInput('2000-12-31'),
      fixedAssetAssessmentUpdatePage.setNameOfUserInput('nameOfUser'),
      fixedAssetAssessmentUpdatePage.setFixedAssetPictureInput(absolutePath),
      fixedAssetAssessmentUpdatePage.setFixedAssetItemIdInput('5'),
      fixedAssetAssessmentUpdatePage.setEstimatedValueInput('5'),
      fixedAssetAssessmentUpdatePage.setEstimatedUsefulMonthsInput('5')
    ]);
    expect(await fixedAssetAssessmentUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );
    expect(await fixedAssetAssessmentUpdatePage.getAssessmentDateInput()).to.eq(
      '2000-12-31',
      'Expected assessmentDate value to be equals to 2000-12-31'
    );
    expect(await fixedAssetAssessmentUpdatePage.getAssessmentRemarksInput()).to.eq(
      'assessmentRemarks',
      'Expected AssessmentRemarks value to be equals to assessmentRemarks'
    );
    expect(await fixedAssetAssessmentUpdatePage.getNameOfAssessingStaffInput()).to.eq(
      'nameOfAssessingStaff',
      'Expected NameOfAssessingStaff value to be equals to nameOfAssessingStaff'
    );
    expect(await fixedAssetAssessmentUpdatePage.getNameOfAssessmentContractorInput()).to.eq(
      'nameOfAssessmentContractor',
      'Expected NameOfAssessmentContractor value to be equals to nameOfAssessmentContractor'
    );
    expect(await fixedAssetAssessmentUpdatePage.getCurrentServiceOutletCodeInput()).to.eq(
      'currentServiceOutletCode',
      'Expected CurrentServiceOutletCode value to be equals to currentServiceOutletCode'
    );
    expect(await fixedAssetAssessmentUpdatePage.getCurrentPhysicalAddressInput()).to.eq(
      'currentPhysicalAddress',
      'Expected CurrentPhysicalAddress value to be equals to currentPhysicalAddress'
    );
    expect(await fixedAssetAssessmentUpdatePage.getNextAssessmentDateInput()).to.eq(
      '2000-12-31',
      'Expected nextAssessmentDate value to be equals to 2000-12-31'
    );
    expect(await fixedAssetAssessmentUpdatePage.getNameOfUserInput()).to.eq(
      'nameOfUser',
      'Expected NameOfUser value to be equals to nameOfUser'
    );
    expect(await fixedAssetAssessmentUpdatePage.getFixedAssetPictureInput()).to.endsWith(
      fileNameToUpload,
      'Expected FixedAssetPicture value to be end with ' + fileNameToUpload
    );
    expect(await fixedAssetAssessmentUpdatePage.getFixedAssetItemIdInput()).to.eq('5', 'Expected fixedAssetItemId value to be equals to 5');
    expect(await fixedAssetAssessmentUpdatePage.getEstimatedValueInput()).to.eq('5', 'Expected estimatedValue value to be equals to 5');
    expect(await fixedAssetAssessmentUpdatePage.getEstimatedUsefulMonthsInput()).to.eq(
      '5',
      'Expected estimatedUsefulMonths value to be equals to 5'
    );
    await fixedAssetAssessmentUpdatePage.save();
    expect(await fixedAssetAssessmentUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await fixedAssetAssessmentComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last FixedAssetAssessment', async () => {
    const nbButtonsBeforeDelete = await fixedAssetAssessmentComponentsPage.countDeleteButtons();
    await fixedAssetAssessmentComponentsPage.clickOnLastDeleteButton();

    fixedAssetAssessmentDeleteDialog = new FixedAssetAssessmentDeleteDialog();
    expect(await fixedAssetAssessmentDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Fixed Asset Assessment?');
    await fixedAssetAssessmentDeleteDialog.clickOnConfirmButton();

    expect(await fixedAssetAssessmentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
