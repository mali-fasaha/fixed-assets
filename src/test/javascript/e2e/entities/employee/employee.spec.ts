/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { EmployeeComponentsPage, EmployeeDeleteDialog, EmployeeUpdatePage } from './employee.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Employee e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let employeeUpdatePage: EmployeeUpdatePage;
  let employeeComponentsPage: EmployeeComponentsPage;
  let employeeDeleteDialog: EmployeeDeleteDialog;
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

  it('should load Employees', async () => {
    await navBarPage.goToEntity('employee');
    employeeComponentsPage = new EmployeeComponentsPage();
    await browser.wait(ec.visibilityOf(employeeComponentsPage.title), 5000);
    expect(await employeeComponentsPage.getTitle()).to.eq('Employees');
  });

  it('should load create Employee page', async () => {
    await employeeComponentsPage.clickOnCreateButton();
    employeeUpdatePage = new EmployeeUpdatePage();
    expect(await employeeUpdatePage.getPageTitle()).to.eq('Create or edit a Employee');
    await employeeUpdatePage.cancel();
  });

  it('should create and save Employees', async () => {
    const nbButtonsBeforeCreate = await employeeComponentsPage.countDeleteButtons();

    await employeeComponentsPage.clickOnCreateButton();
    await promise.all([
      employeeUpdatePage.setEmployeeNameInput('employeeName'),
      employeeUpdatePage.setServiceOutletCodeInput('serviceOutletCode'),
      employeeUpdatePage.setEmployeeRoleInput('employeeRole'),
      employeeUpdatePage.setEmployeeStaffCodeInput('employeeStaffCode'),
      employeeUpdatePage.setEmployeeSignatureInput(absolutePath),
      employeeUpdatePage.setEmployeeEmailInput('employeeEmail')
    ]);
    expect(await employeeUpdatePage.getEmployeeNameInput()).to.eq(
      'employeeName',
      'Expected EmployeeName value to be equals to employeeName'
    );
    expect(await employeeUpdatePage.getServiceOutletCodeInput()).to.eq(
      'serviceOutletCode',
      'Expected ServiceOutletCode value to be equals to serviceOutletCode'
    );
    expect(await employeeUpdatePage.getEmployeeRoleInput()).to.eq(
      'employeeRole',
      'Expected EmployeeRole value to be equals to employeeRole'
    );
    expect(await employeeUpdatePage.getEmployeeStaffCodeInput()).to.eq(
      'employeeStaffCode',
      'Expected EmployeeStaffCode value to be equals to employeeStaffCode'
    );
    expect(await employeeUpdatePage.getEmployeeSignatureInput()).to.endsWith(
      fileNameToUpload,
      'Expected EmployeeSignature value to be end with ' + fileNameToUpload
    );
    expect(await employeeUpdatePage.getEmployeeEmailInput()).to.eq(
      'employeeEmail',
      'Expected EmployeeEmail value to be equals to employeeEmail'
    );
    await employeeUpdatePage.save();
    expect(await employeeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await employeeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Employee', async () => {
    const nbButtonsBeforeDelete = await employeeComponentsPage.countDeleteButtons();
    await employeeComponentsPage.clickOnLastDeleteButton();

    employeeDeleteDialog = new EmployeeDeleteDialog();
    expect(await employeeDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Employee?');
    await employeeDeleteDialog.clickOnConfirmButton();

    expect(await employeeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
