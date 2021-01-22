import { Component, OnInit } from '@angular/core';
import { CulturalOfferType} from '../../../models/cultural-offer-type';
import { CulturalOfferSubType} from '../../../models/culutral-offer-subType';
import { CulturalOfferTypeService,  CulturalOfferSubTypeService } from '../../../services';
import { CreateTypeFormComponent } from '../create-type-form/create-type-form.component';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { UpdateTypeFormComponent } from 'src/app/modules/cultural-offer-type/update-type-form/update-type-form.component';
import { ConfirmDeleteComponent } from 'src/app/core/confirm-delete/confirm-delete.component';
import { SubTypePage, TypePage } from 'src/app/models/type-page';
import { SnackBarComponent } from 'src/app/core/snack-bar/snack-bar.component';

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
        .getAllPaginated(this.curentPageType,this.pageSize).subscribe((res) => {
          this.typePage = res;
          this.culturalOfferTypes = res.content;
        });
  }
  loadSubTypes(typeId: number){
    this.curentType = typeId;
    this.subTypeService
        .getAllPaginated(this.curentPageSubType, this.pageSize,typeId)
        .subscribe( (res) => {
          this.subTypePage = res;
          this.culturalOfferSubTypes = res.content
        });
  }

  getNextType(): void{
    this.curentPageType++;
    this.loadTypes();
  }

  getPreviousType():void{
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
      panelClass : "mat-elevation-z8",
      data: {}
    });
    dialogRef
      .afterClosed()
      .subscribe(confirmed =>{
        if(confirmed){
          this.afterDeleteClosed(typeId);
        }
      })
  }
  afterDeleteClosed(typeId: any){
    this.typeService
        .deleteType(typeId)
        .subscribe(()=>{
          this.loadTypes();
          this.snackBar.openSnackBar("Deleted successfully", "", "green-snackbar");
        });
  }

  openCreateDialog(event: any){
    const dialogRef = this.dialog.open(CreateTypeFormComponent, {
      width: '300px',
      panelClass : "mat-elevation-z8",
      data: {_id: 1, name: ""}
    });
    this.curentPageType = this.typePage.totalPages - 1;
    dialogRef.afterClosed().subscribe(name => {
      if(name != undefined){
        this.afterCreateClosed(name);
      }
    });
  }

  afterCreateClosed(name: any){
    let req: CulturalOfferType = {id:1, name: name};
    this.typeService.createType(req)
        .subscribe(() => {
          this.loadTypes();
          this.snackBar.openSnackBar("Created successfully", "", "green-snackbar");
        });
  }

  openUpdateDialog(updatedType:any):void {
    const dialogRef = this.dialog.open(UpdateTypeFormComponent, {
      width: '300px',
      panelClass : "mat-elevation-z8",
      data: {id: updatedType.id, name: updatedType.name}
    });
    //bolje sort na back
    this.curentPageType = this.typePage.totalPages - 1;

    dialogRef.afterClosed().subscribe(result => {
      if(result != undefined){
        let req: CulturalOfferType = {id: result.id, name: result.name};
        this.typeService.updateType(req)
            .subscribe((response) => {
              this.loadTypes();
              this.loadSubTypes(response.id);
            });
      }
    });
  }

}
