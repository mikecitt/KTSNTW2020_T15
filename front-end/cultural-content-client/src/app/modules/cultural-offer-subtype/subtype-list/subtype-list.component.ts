import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { ConfirmDeleteComponent } from 'src/app/core/confirm-delete/confirm-delete.component';
import { SnackBarComponent } from 'src/app/core/snack-bar/snack-bar.component';
import { CulturalOfferSubType } from 'src/app/models/culutral-offer-subType';
import { CulturalOfferSubTypeService } from 'src/app/services';
import { SubtypeFormComponent } from '../subtype-form/subtype-form.component';

@Component({
  selector: 'app-subtype-list',
  templateUrl: './subtype-list.component.html',
  styleUrls: ['./subtype-list.component.scss']
})
export class SubtypeListComponent implements OnInit {
  //TODO paginacija

  @Input()
  offerSubtypes: CulturalOfferSubType[];
  @Input()
  typeId: number;

  @Output()
  refreshDataEvent = new EventEmitter<any>();

  displayedColumns: string[] = ['name', 'type.name', 'options'];

  dataSource: MatTableDataSource<CulturalOfferSubType>;

  constructor(
    private subTypeService: CulturalOfferSubTypeService,
    private dialog: MatDialog,
    private snackBar: SnackBarComponent
    ) { }

  ngOnChanges(): void{
    this.dataSource = new MatTableDataSource<CulturalOfferSubType>(this.offerSubtypes);
  }

  ngOnInit(): void {
  }

  createDialog(data: SubTypeDialogData): MatDialogRef<SubtypeFormComponent>{
    const dialogRef = this.dialog.open(SubtypeFormComponent, {
      width: '300px',
      panelClass : "mat-elevation-z8",
      data: data
    });
    return dialogRef;
  }

  createSubType(): void{
    let data: SubTypeDialogData = {
      subType: {id: 0, name: '', type: {id: this.typeId, name:''}},
      formType: 'CREATE'
    };
    const dialogRef = this.createDialog(data);
    dialogRef
      .afterClosed()
      .subscribe( (result) => {
        if(result){
          this.subTypeService.createSubType(result)
              .subscribe(() => { this.refreshDataEvent.emit(result.type.id) });
        }
      });
  }

  deleteSubType(elem: CulturalOfferSubType): void{
    const dialogRef = this.dialog.open(ConfirmDeleteComponent, {
      width: '300px',
      panelClass : "mat-elevation-z8",
      data: {}
    });
    dialogRef
      .afterClosed()
      .subscribe(result =>{
        if(result){
          this.afterDeleteClosed(elem);
        }
      })
  }

  afterDeleteClosed(subType: CulturalOfferSubType){
    this.subTypeService
        .deleteSubType(subType)
        .subscribe(() => {
          this.snackBar.openSnackBar("Deleted successfully", "", "green-snackbar");
          this.refreshDataEvent.emit(subType.type.id);
        })
  }

  updateSubType(elem: CulturalOfferSubType): void{
    let data: SubTypeDialogData = {
      subType: {id: elem.id , name: elem.name, type: {id: elem.type.id, name:''}},
      formType: 'UPDATE'
    };
    const dialogRef = this.createDialog(data);
    dialogRef
      .afterClosed()
      .subscribe( (result) => {
        if(result){
          this.subTypeService.updateSubType(result)
              .subscribe(() => { this.refreshDataEvent.emit(result.type.id) });
        }
      });
  }
}

export interface SubTypeDialogData{
  subType: CulturalOfferSubType,
  formType: string
}
