import { Component, OnInit } from '@angular/core';
import { CulturalOfferType, TypeDialogData} from '../../../models/cultural-offer-type';
import { CulturalOfferSubType} from '../../../models/culutral-offer-subType';
import { CulturalOfferTypeService,  CulturalOfferSubTypeService } from '../../../services';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { ConfirmDeleteComponent } from 'src/app/core/confirm-delete/confirm-delete.component';
import { SubTypePage, TypePage } from 'src/app/models/type-page';
import { SnackBarComponent } from 'src/app/core/snack-bar/snack-bar.component';
import { TypeFormComponent } from '../type-form/type-form.component';

@Component({
  selector: 'app-cultural-offer-type-page',
  templateUrl: './cultural-offer-type-page.component.html',
  styleUrls: ['./cultural-offer-type-page.component.scss']
})
export class CulturalOfferTypePageComponent implements OnInit {

  culturalOfferTypes: CulturalOfferType[];
  culturalOfferSubTypes: CulturalOfferSubType[];
  pageSize: number;

  curentType: number;
  typePage: TypePage;
  curentPageType: number;

  subTypePage: SubTypePage;
  curentPageSubType: number;

  constructor(
    private typeService: CulturalOfferTypeService, public dialog: MatDialog,
    public snackBar: SnackBarComponent,
    private subTypeService: CulturalOfferSubTypeService)
  {
    this.curentPageType = 0;
    this.curentPageSubType = 0;
    this.pageSize = 5;
  }

  ngOnInit(): void {
    this.loadTypes();
  }

  loadTypes(): void{
    this.typeService
        .getAllPaginated(this.curentPageType, this.pageSize).subscribe((res) => {
          this.typePage = res;
          this.culturalOfferTypes = res.content;
        });
  }
  loadSubTypes(typeId: number){
    this.curentType = typeId;
    this.subTypeService
        .getAllPaginated(this.curentPageSubType, this.pageSize, typeId)
        .subscribe( (res) => {
          this.subTypePage = res;
          this.culturalOfferSubTypes = res.content;
        });
  }

  getNextType(): void{
    this.curentPageType++;
    this.loadTypes();
  }

  getPreviousType(): void{
    this.curentPageType--;
    this.loadTypes();
  }

  getNextSubType(): void{
    this.curentPageSubType++;
    this.loadSubTypes(this.curentType);
  }

  getPreviousSubType(): void{
    this.curentPageSubType--;
    this.loadSubTypes(this.curentType);
  }

  deleteType(typeId: any): void{
    const dialogRef = this.dialog.open(ConfirmDeleteComponent, {
      width: '300px',
      panelClass : 'mat-elevation-z8',
      data: {}
    });
    dialogRef
      .afterClosed()
      .subscribe(confirmed => {
        if (confirmed){
          this.afterDeleteClosed(typeId);
        }
      });
  }
  afterDeleteClosed(typeId: any){
    this.typeService
        .deleteType(typeId)
        .subscribe(() => {
          this.loadTypes();
          this.snackBar.openSnackBar('Deleted successfully', '', 'green-snackbar');
        });
  }

  openDialog(data: TypeDialogData): MatDialogRef<TypeFormComponent>{
    const dialogRef = this.dialog.open(TypeFormComponent, {
      width: '300px',
      panelClass : 'mat-elevation-z8',
      data
    });
    return dialogRef;
  }

  openCreateDialog(event: any){
    const dialogData: TypeDialogData = {
      formType: 'CREATE',
      type: {
        id: 0,
        name: ''
      }
    };
    const dialogRef = this.openDialog(dialogData);
    this.curentPageType = this.typePage.totalPages - 1;
    dialogRef.afterClosed().subscribe(result => {
      if (result){
        this.afterCreateClosed(result.name);
      }
    });
  }

  afterCreateClosed(name: any){
    const req: CulturalOfferType = {id: 1, name};
    this.typeService.createType(req)
        .subscribe(() => {
          this.loadTypes();
          this.snackBar.openSnackBar('Created successfully', '', 'green-snackbar');
        });
  }

  openUpdateDialog(updatedType: any): void {
    const dialogData: TypeDialogData = {
      formType: 'UPDATE',
      type: {
        id: updatedType.id,
        name: updatedType.name
      }
    };
    const dialogRef = this.openDialog(dialogData);
    // bolje sort na back
    this.curentPageType = this.typePage.totalPages - 1;
    dialogRef.afterClosed().subscribe((result: CulturalOfferType) => {
      if (result){
        this.typeService.updateType(result)
            .subscribe((response) => {
              this.loadTypes();
              this.loadSubTypes(response.id);
              this.snackBar.openSnackBar('Updated successfully', '', 'green-snackbar');
            });
      }
    });
  }

}
