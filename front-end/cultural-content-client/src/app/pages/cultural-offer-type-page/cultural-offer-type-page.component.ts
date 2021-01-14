import { Component, OnInit } from '@angular/core';
import { CulturalOfferType} from '../../model/cultural-offer-type';
import { CulturalOfferSubType} from '../../model/culutral-offer-subType';
import { CulturalOfferTypeService } from '../../service/cultural-offer-type/cultural-offer-type.service';
import { CreateTypeFormComponent } from '../../create-type-form/create-type-form.component';
import { MatDialog } from '@angular/material/dialog';
import { UpdateTypeFormComponent } from 'src/app/update-type-form/update-type-form.component';
import { ConfirmDeleteComponent } from 'src/app/confirm-delete/confirm-delete.component';
import { TypePage } from 'src/app/model/type-page';

@Component({
  selector: 'app-cultural-offer-type-page',
  templateUrl: './cultural-offer-type-page.component.html',
  styleUrls: ['./cultural-offer-type-page.component.scss']
})
export class CulturalOfferTypePageComponent implements OnInit {

  culturalOfferTypes: CulturalOfferType[];
  culturalOfferSubTypes: CulturalOfferSubType[];

  typePage: TypePage;
  curentPage: number = 0;
  pageSize: number = 5;

  constructor(
    private typeService: CulturalOfferTypeService, public dialog: MatDialog
  ) { }

  loadCulturalOfferTypes(): void{
    this.typeService
        .getAllPaginated(this.curentPage,this.pageSize).subscribe((res) => {
          this.typePage = res;
          this.culturalOfferTypes = res.content
        });
  }

  getNextType(): void{
    this.curentPage++;
    this.loadCulturalOfferTypes();
  }

  getPreviousType():void{
    this.curentPage--;
    this.loadCulturalOfferTypes();
  }

  ngOnInit(): void {
    this.loadCulturalOfferTypes();
  }
  deleteType(typeId: any): void{
    console.log(typeId);
    const dialogRef = this.dialog.open(ConfirmDeleteComponent, {
      width: '300px',
      panelClass : "mat-elevation-z8",
      data: {}
    });
    dialogRef.afterClosed()
             .subscribe(result =>{
                if(result){
                  this.typeService
                      .deleteType(typeId)
                      .subscribe((response)=>{
                        this.loadCulturalOfferTypes();
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
        let req: CulturalOfferType = {_id:1, name: result};
        this.typeService.saveType(req)
            .subscribe((response) => {
              this.loadCulturalOfferTypes();
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
    dialogRef.afterClosed().subscribe(result => {
      if(result != undefined){
        let req: CulturalOfferType = {_id: result.id, name: result.name};
        this.typeService.updateType(req)
            .subscribe((response) => {
              this.loadCulturalOfferTypes();
            },
            (error) => {
              alert(error.error)
            });
      }
    });
  }

}
