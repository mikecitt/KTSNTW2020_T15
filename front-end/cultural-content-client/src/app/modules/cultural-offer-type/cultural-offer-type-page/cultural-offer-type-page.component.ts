import { Component, OnInit } from '@angular/core';
import { CulturalOfferType} from '../../../models/cultural-offer-type';
import { CulturalOfferSubType} from '../../../models/culutral-offer-subType';
import { CulturalOfferTypeService } from '../../../services';
import { CreateTypeFormComponent } from '../create-type-form/create-type-form.component';
import { MatDialog } from '@angular/material/dialog';
import { UpdateTypeFormComponent } from 'src/app/modules/cultural-offer-type/update-type-form/update-type-form.component';
import { ConfirmDeleteComponent } from 'src/app/core/confirm-delete/confirm-delete.component';
import { TypePage } from 'src/app/models/type-page';

@Component({
  selector: 'app-cultural-offer-type-page',
  templateUrl: './cultural-offer-type-page.component.html',
  styleUrls: ['./cultural-offer-type-page.component.scss']
})
export class CulturalOfferTypePageComponent implements OnInit {

  culturalOfferTypes: CulturalOfferType[];
  culturalOfferSubTypes: CulturalOfferSubType[];

  typePage: TypePage;
  curentPage: number;
  pageSize: number;

  constructor(
    private typeService: CulturalOfferTypeService, public dialog: MatDialog)
  {
    this.curentPage = 0;
    this.pageSize = 5;
  }

  ngOnInit(): void {
    this.loadTypes();
  }

  loadTypes(): void{
    this.typeService
        .getAllPaginated(this.curentPage,this.pageSize).subscribe((res) => {
          this.typePage = res;
          this.culturalOfferTypes = res.content
        });
  }

  getNextType(): void{
    this.curentPage++;
    this.loadTypes();
  }

  getPreviousType():void{
    this.curentPage--;
    this.loadTypes();
  }

  deleteType(typeId: any): void{
    const dialogRef = this.dialog.open(ConfirmDeleteComponent, {
      width: '300px',
      panelClass : "mat-elevation-z8",
      data: {}
    });
    dialogRef
      .afterClosed()
      .subscribe(result =>{
        if(result){
          this.typeService
              .deleteType(typeId)
              .subscribe((response)=>{
                this.loadTypes();
                alert("Deleted successfully");
              },
              (error) => alert(error.error));
        }
      })
  }

  openCreateDialog(event:any){
    const dialogRef = this.dialog.open(CreateTypeFormComponent, {
      width: '300px',
      panelClass : "mat-elevation-z8",
      data: {_id: 1, name: ""}
    });
    this.curentPage = this.typePage.totalPages - 1;
    dialogRef.afterClosed().subscribe(result => {
      if(result != undefined){
        let req: CulturalOfferType = {id:1, name: result};
        this.typeService.createType(req)
            .subscribe((response) => {
              this.loadTypes();
            },
            (error) => {
              alert(error.error)
            });
      }
    });
  }

  openUpdateDialog(updatedType:any):void {
    const dialogRef = this.dialog.open(UpdateTypeFormComponent, {
      width: '300px',
      panelClass : "mat-elevation-z8",
      data: {id: updatedType.id, name: updatedType.name}
    });
    //bolje sort na back
    this.curentPage = this.typePage.totalPages - 1;

    dialogRef.afterClosed().subscribe(result => {
      if(result != undefined){
        let req: CulturalOfferType = {id: result.id, name: result.name};
        this.typeService.updateType(req)
            .subscribe((response) => {
              this.loadTypes();
            },
            (error) => {
              alert(error.error)
            });
      }
    });
  }

}
