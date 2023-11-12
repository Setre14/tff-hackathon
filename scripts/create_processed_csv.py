import sys
import os
import shutil

from create_mastercard import create_mastercard_csv
from create_event import create_events_csv

if len(sys.argv) < 2:
    print("Please provide the path to the data!")
    sys.exit(1)

data_path = sys.argv[1]

out_path = os.path.join(os.getcwd(),"data")
if os.path.exists(out_path):
    shutil.rmtree(out_path)
os.makedirs(out_path)

inveniumDataName = "InveniumData"
mastercardDataName = "MastercardData"
tourismDataName = "TourismData"

subfolders = [inveniumDataName, mastercardDataName, tourismDataName]

for subfolder in subfolders:
    sub_data_path = os.path.join(data_path, subfolder)

    if not os.path.isdir(sub_data_path):
        print(f"Sub dir {sub_data_path} does not exit")
        sys.exit(1)

create_mastercard_csv(os.path.join(data_path, mastercardDataName), out_path)
create_events_csv(os.path.join(data_path, inveniumDataName), out_path)
